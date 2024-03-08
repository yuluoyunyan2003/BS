package bs.www.service.Impl;

import bs.www.pojo.Goods;
import bs.www.pojo.User;
import bs.www.repository.MongoRepository;
import bs.www.repository.RedisRepository;
import bs.www.service.UserService;
import bs.www.utils.JwtUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author w雨落云烟w
 * @date 2023/12/14 18:43
 * @description: 接口UserService的实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    private WebClient webClient = WebClient.create();

    /**
     * @description 首先用js_code拿到openid，然后去Redis确认其存在，
     */
    @Override
    public HashMap<String, Object> login(String js_code) {
        String openid = getOpenid(js_code);
        String jwt = getJWT(openid);

        HashMap resdisRes = userExistInRedis(jwt, openid);
        //如果redisRes不为空，则表明Redis存在该JWT，直接返回即可
        if (resdisRes != null) {
            return resdisRes;
        }

        if (!mongoRepository.isUserExist(openid)) {
            //（针对 第一次使用本app的用户）在redis和mongo都没有该用户的情况下，新增用户，并返回其余空数据。
            User user = new User();
            user.setOpenid(openid);
            mongoRepository.createUser(user);
        }

        //（针对 第一次&清除了本地缓存的用户）查询结束后，需将JWT保存到Redis，方便拦截器验证用户访问合法性
        redisRepository.setJWT(jwt);
        return getData(jwt, openid);
    }

    @Override
    public HashMap<String, Object> checkLogin(String jwt) {
        String openid = null;
        try {
            openid = JwtUtil.parseToken(jwt).get("openid").toString();
        } catch (Exception e) {
            throw new RuntimeException("出错了：" + e);
        }

        HashMap resdisRes = userExistInRedis(jwt, openid);
        //如果redisRes不为空，则表明Redis存在该JWT，直接返回即可
        if (resdisRes != null) {
            return resdisRes;
        }

        if (!mongoRepository.isUserExist(openid)) {
            //（针对 非法操作的用户）在redis和mongo都没有该用户的情况下，直接返回null。
            return null;
        }

        //（针对 正常高频使用本app的用户）此时Redis无但mongo有的情况下，将JWT保存到Redis，方便拦截器验证用户访问合法性
        redisRepository.setJWT(jwt);
        return getData(jwt, openid);
    }

    @Override
    public ArrayList getShoppingCart(String openid) {
        Query query = new Query(Criteria.where("openid").is(openid));
        return mongoTemplate.findOne(query, User.class).getShoppingCart();
    }

    @Override
    public void updateShoppingCart(String jwt, ArrayList<String> newShoppingCart) {
        String openid = JwtUtil.parseToken(jwt).get("openid").toString();
        Query query = new Query(Criteria.where("openid").is(openid));
        Update update = new Update().set("shoppingCart", newShoppingCart);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void createOrders(String jwt, String goodsId) {
        String openid = JwtUtil.parseToken(jwt).get("openid").toString();
        Query query = new Query(Criteria.where("openid").is(openid));
        User.Orders newOrders = new User.Orders();
        newOrders.setArrival(false);
        newOrders.setReturn(false);
        newOrders.setGoodsID(goodsId);
        Update update = new Update().push("orders", newOrders);

        try {
            mongoTemplate.updateFirst(query, update, User.class);
        } catch (Exception e) {
            throw new Error("createOrders失败！");
        }
    }

    @Override
    public void deleteOrders(String jwt, String ordersId) {
        String openid = JwtUtil.parseToken(jwt).get("openid").toString();
        Query query = new Query(Criteria.where("openid").is(openid));
        Update update = new Update().pull("orders", new BasicDBObject("ordersId", ordersId));

        UpdateResult res = mongoTemplate.updateFirst(query, update, User.class);
        System.out.println("MatchedCount："+res.getMatchedCount());
        System.out.println("ModifiedCount："+res.getModifiedCount());
        System.out.println("UpsertedId："+res.getUpsertedId());
    }

    @Override
    public void updateOrders(String jwt, ArrayList newOrders) {
        String openid = JwtUtil.parseToken(jwt).get("openid").toString();
        Query query = new Query(Criteria.where("openid").is(openid));
        Update update = new Update().set("orders", newOrders);

        try {
            mongoTemplate.updateFirst(query, update, User.class);
        } catch (Exception e) {
            throw new Error("updateOrders出错！");
        }
    }

    @Override
    public ArrayList<User.Orders> getOrders(String jwt) {
        String openid = JwtUtil.parseToken(jwt).get("openid").toString();
        Query query = new Query(Criteria.where("openid").is(openid));
        Update update = new Update();
        mongoTemplate.updateFirst(query, update, User.class);
        return mongoTemplate.findOne(query, User.class).getOrders();
    }

    @Override
    public void updateDeliveryAddress(String jwt, Object newDeliveryAddress) {
        String openid = JwtUtil.parseToken(jwt).get("openid").toString();
        Query query = new Query(Criteria.where("openid").is(openid));
        Update update = new Update().set("deliveryAddress", newDeliveryAddress);

        try {
            mongoTemplate.updateFirst(query, update, User.class);
        } catch (Exception e) {
            throw new Error("updateDeliveryAddress出错！");
        }
    }

    @Override
    public ArrayList<User.Address> getDeliveryAddress(String jwt) {
        String openid = JwtUtil.parseToken(jwt).get("openid").toString();
        Query query = new Query(Criteria.where("openid").is(openid));
        return mongoTemplate.findOne(query, User.class).getDeliveryAddress();
    }

    @Override
    public void submitFeedback(String jwt, String feedback) {
        String openid = JwtUtil.parseToken(jwt).get("openid").toString();
        Query query = new Query(Criteria.where("openid").is(openid));
        Update update = new Update().push("feedback", feedback);

        try {
            mongoTemplate.updateFirst(query, update, User.class);
        } catch (Exception e) {
            throw new Error("submitFeedback出错！");
        }
    }

    @Override
    public ArrayList<Goods> findGoods(String goodsName) {
        Pattern pattern = Pattern.compile("^.*" + goodsName.toString() + ".*$", Pattern.CASE_INSENSITIVE);
        Query query = Query.query(Criteria.where("name").regex(pattern));
        return (ArrayList<Goods>) mongoTemplate.find(query, Goods.class, "goods");
    }

    /**
     * @param js_code 前端通过wx.login()获取到的值，用以获取openid
     * @return openid
     * @description 向微信服务接口发起GET请求，并提取返回值的openid（其返回值包括openid和session_key，但本方法只向外返回openid）
     */
    private String getOpenid(String js_code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?"
                + "appid=wx422256ddea360f82"
                + "&secret=f581de9330775823b1a5b67d847964c0"
                + "&js_code=" + js_code
                + "&grant_type=authorization_code";

        //发送请求
        Mono<String> mono = webClient.get().uri(url).retrieve().bodyToMono(String.class);
        //将String类型的响应值mono转为JSON类型
        JSONObject obj = new JSONObject(mono.block());
        //通过JSON类型的响应值获取openid并转换成String类型
        return (String) obj.get("openid");

    }

    /**
     * @param openid 小程序用户唯一表示符
     * @return jwt
     * @description 凭openid生成jwt
     */
    private String getJWT(String openid) {
        //生成JWT令牌所需参数集合claims
        Map<String, Object> claims = new HashMap<>();
        //将id添加到参数claims中
        claims.put("openid", openid);
        //生成JWT令牌
        return JwtUtil.genToken(claims);
    }

    /**
     * @param jwt JWT
     * @return getData(jwt, openid)
     * @Param openid
     * @description 封装用户在Redis的存在性检测
     */
    private HashMap<String, Object> userExistInRedis(String jwt, String openid) {
        //如果Redis有该jwt，则mongo不作用户存在判断，直接去mongo拿其余数据；否则mongo判断用户是否存在。
        if (redisRepository.isJWTExist(jwt)) {
            //（待！）判断缓存过期时间，少于1小时(60秒60分)则更新过期时间，
            if (redisRepository.getExpire(jwt) < 60 * 60) {
                redisRepository.upDateJWTExpire(jwt);
            }
            return getData(jwt, openid);
        }
        return null;
    }

    /**
     * @param jwt
     * @param openid
     * @return res 包括homeData,cartData,ordersData,JWT
     * @description 拿数据，封装数据
     */
    private HashMap<String, Object> getData(String jwt, String openid) {
        List<Goods> homeData = mongoRepository.getHomeData();
        List<Goods> cartData = mongoRepository.getShoppingCart(openid);
        List<Goods> ordersData = mongoRepository.getOrders(openid);

        HashMap<String, Object> res = new HashMap<>();
        res.put("jwt", jwt);
        res.put("homeData", homeData);
        res.put("cartData", cartData);
        res.put("ordersData", ordersData);
        return res;
    }
}
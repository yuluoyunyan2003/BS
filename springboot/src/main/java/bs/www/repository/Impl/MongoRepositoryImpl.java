package bs.www.repository.Impl;

import bs.www.repository.MongoRepository;
import bs.www.utils.JwtUtil;
import bs.www.pojo.Goods;
import bs.www.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author w雨落云烟w
 * @date 2023/12/23 20:17
 * @description:
 */
@Repository
public class MongoRepositoryImpl implements MongoRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void createUser(User user) {
        mongoTemplate.insert(user);
        System.out.println("新增用户成功！");
    }

    @Override
    public void deleteUser(String openid) {

    }

    @Override
    public User updateUser(String openid, User newUser) {
        return null;
    }

    @Override
    public User findUser(String openid) {
        Query query = new Query(Criteria.where("openid").is(openid));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public Boolean isUserExist(String openid) {
        Query query = new Query(Criteria.where("openid").is(openid));
        return mongoTemplate.exists(query, User.class);
    }

    @Override
    public void createGoods(Goods goods) {

    }

    @Override
    public void deleteGoods(String openid, String ordersId) {

    }

    @Override
    public User updateGoods(String openid, Goods newGoods) {
        return null;
    }

    @Override
    public ArrayList findGoods(String goodsName) {
        return null;
    }

    @Override
    public void createOrders(User.Orders orders) {

    }

    @Override
    public void deleteOrders(String openid, String ordersId) {

    }

    @Override
    public User updateOrders(String goodsId, Goods newOrders) {
        return null;
    }

    @Override
    public List<Goods> getHomeData() {
        return mongoTemplate.findAll(Goods.class);
    }

    @Override
    public ArrayList getShoppingCart(String openid) {
        return null;
    }

    @Override
    public void updateDeliveryAddress(String openid, ArrayList newDeliveryAddressManagement) {

    }

    @Override
    public ArrayList getDeliveryAddressManagement(String openid) {
        return null;
    }

    @Override
    public ArrayList getOrders(String openid) {
        Query query = new Query(Criteria.where("openid").is(openid));
        User userInfo = mongoTemplate.findOne(query, User.class);

        //获取ordersData的原始集合
        ArrayList userOrdersIdArray = userInfo.getOrders();
        if (userOrdersIdArray == null) {
            System.out.println("此人的ordersData为空！");
            return null;
        }

        ArrayList ordersTemp = new ArrayList<>();
        for (Object element : userOrdersIdArray) {
            User.Orders res = (User.Orders) element;
            Goods goods = mongoTemplate.findById(res.getGoodsID(), Goods.class, "goods");

            Map mapTemp = new HashMap<>();
            mapTemp.put("isArrival", ((User.Orders) element).isArrival());
            mapTemp.put("isReturn", ((User.Orders) element).isReturn());
            mapTemp.put("orders", goods);
            ordersTemp.add(mapTemp);
        }
        return ordersTemp;
    }

    @Override
    public void updateShoppingCart(String openid, ArrayList newShoppingCart) {

    }

    //！！！待定！！！
    @Override
    public void submitFeedback(String openid, String feedback) {
        Query query = new Query(Criteria.where("openid").is(openid));
        Update update = Update.update("feedback", feedback);
        mongoTemplate.updateFirst(query, update, "user");
    }

    @Override
    public ArrayList getFeedBack() {
        return null;
    }

}

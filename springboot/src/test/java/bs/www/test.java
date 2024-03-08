package bs.www;

import bs.www.pojo.Goods;
import bs.www.pojo.User;
import bs.www.repository.MongoRepository;
import bs.www.repository.RedisRepository;
import bs.www.service.UserService;
import com.mongodb.internal.operation.FindAndReplaceOperation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class test {

    @Autowired
    private RedisRepository redisRepository;
    private WebClient webClient = WebClient.create();
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserService userService;

    @Test
    public void testMethod() {
//        String openid = "odi2K6yJyRagFbtcWHsgUF2N9Tqw";
//        ArrayList arrayList = new ArrayList<>();
//        arrayList.add("1015111");
//        arrayList.add("asdawe");
//        userService.updateShoppingCart(openid, arrayList);
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsib3BlbmlkIjoib2RpMks2MEFFamdXaXRHOVpSeVZ1MUJmM2ozYyJ9LCJleHAiOjE3MDk3NjczMDR9.DsviifJ3u-dBnbkLQocHUUEZHvPDlf1gf0aNuDMIST0";
        String ordersId = "65e8e3420ee475a67121b013";
        userService.deleteOrders(jwt, ordersId);
    }
}
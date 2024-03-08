package bs.www.repository;

import bs.www.pojo.Goods;
import bs.www.pojo.User;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * @author w雨落云烟w
 * @date 2023/12/23 20:16
 * @description: MongoDB的CRUD接口
 */
public interface MongoRepository {

    void createUser(User user);

    void deleteUser(String openid);

    User updateUser(String openid, User newUser);

    User findUser(String openid);

    Boolean isUserExist(String openid);

    void createGoods(Goods goods);

    void deleteGoods(String openid, String ordersId);

    User updateGoods(String openid, Goods newGoods);

    ArrayList findGoods(String goodsName);

    void createOrders(User.Orders orders);

    void deleteOrders(String openid, String ordersId);

    User updateOrders(String goodsId, Goods newOrders);

    ArrayList getOrders(String openid);

    void updateShoppingCart(String openid, ArrayList newShoppingCart);

    ArrayList getShoppingCart(String openid);


    void updateDeliveryAddress(String openid, ArrayList newDeliveryAddressManagement);

    ArrayList getDeliveryAddressManagement(String openid);

    void submitFeedback(String openid, String feedback);

    ArrayList getFeedBack();

    List<Goods> getHomeData();
}

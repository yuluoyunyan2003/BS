package bs.www.controller.User;

import bs.www.pojo.User;
import bs.www.service.UserService;
import bs.www.utils.JwtUtil;
import bs.www.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/user")
public class UserOrdersManagement {
    @Autowired
    private UserService userService;

    @PostMapping("/orders")
    public Result createOrders(@RequestHeader("Authorization") String jwt, String goodsId){
        try {
            userService.createOrders(jwt,goodsId);
            return Result.success();
        } catch (Exception e) {
            return Result.error(String.valueOf(e));
        }
    }

    @DeleteMapping("/orders")
    public Result deleteOrders(@RequestHeader("Authorization") String jwt, String ordersId){
        try {
            userService.deleteOrders(jwt, ordersId);
            return Result.success();
        } catch (Exception e) {
            return Result.error(String.valueOf(e));
        }
    }

    @PutMapping("/orders")
    public Result updateOrders(@RequestHeader("Authorization") String jwt, ArrayList newOrders){
        try {
            userService.updateOrders(jwt, newOrders);
            return Result.success();
        } catch (Exception e) {
            return Result.error(String.valueOf(e));
        }
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public Result getOrders(@RequestHeader("Authorization") String jwt) {
        try {
            ArrayList<User.Orders> res = userService.getOrders(jwt);
            return Result.success(res);
        } catch (Exception e) {
            return Result.error(String.valueOf(e));
        }
    }
}

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
public class UserDeliveryAddressManagement {
    @Autowired
    private UserService userService;

    @PutMapping("/deliveryAddress")
    public Result updateDeliveryAddress(@RequestHeader("Authorization") String jwt, @RequestParam("newDeliveryAddress") ArrayList newDeliveryAddress) {
        try {
            userService.updateDeliveryAddress(jwt, newDeliveryAddress);
            return Result.success();
        } catch (Exception e) {
            return Result.error(String.valueOf(e));
        }
    }

    @RequestMapping(value = "/deliveryAddress", method = RequestMethod.GET)
    public Result getDeliveryAddress(@RequestHeader("Authorization") String jwt) {
        try {
            ArrayList<User.Address> res = userService.getDeliveryAddress(jwt);
            return Result.success(res);
        } catch (Exception e) {
            return Result.error(String.valueOf(e));
        }
    }
}

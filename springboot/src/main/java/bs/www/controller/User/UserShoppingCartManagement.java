package bs.www.controller.User;

import bs.www.service.UserService;
import bs.www.utils.JwtUtil;
import bs.www.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserShoppingCartManagement {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public Result getShoppingCart(@RequestHeader("Authorization") String jwt) {
        try {
            List res = userService.getShoppingCart(jwt);
            return Result.success(res);
        } catch (Exception e) {
            return Result.error(String.valueOf(e));
        }
    }

    @PutMapping("/cart")
    public Result updateShoppingCart(@RequestHeader("Authorization") String jwt, @RequestParam("data") ArrayList<String> newShoppingCart) {
        try {
            userService.updateShoppingCart(jwt, newShoppingCart);
            return Result.success("成功！");
        } catch (Exception e) {
            return Result.error(String.valueOf(e));
        }
    }
}

package bs.www.controller.User;

import bs.www.pojo.Goods;
import bs.www.service.UserService;
import bs.www.utils.JwtUtil;
import bs.www.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserOthers {
    @Autowired
    private UserService userService;

    @PostMapping("/feedback")
    public Result submitFeedback(@RequestHeader("Authorization") String jwt, @RequestParam("param") String feedback) {
        try {
            userService.submitFeedback(jwt, feedback);
            return Result.success();
        } catch (Exception e) {
            return Result.error(String.valueOf(e));
        }
    }

    @RequestMapping(value = "/goods/{name}", method = RequestMethod.GET)
    public Result findGoods(@PathVariable("name") String goodsName) {
        try{
            List<Goods> res = userService.findGoods(goodsName);
            return Result.success(res);
        }catch (Exception e){
            return Result.error(String.valueOf(e));
        }
    }
}

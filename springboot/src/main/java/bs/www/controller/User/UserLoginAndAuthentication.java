package bs.www.controller.User;

import bs.www.service.UserService;
import bs.www.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserLoginAndAuthentication {
    @Autowired
    private UserService userService;

    @PostMapping("login-log")
    public Result login(@RequestParam("js_code") String js_code) {
        try {
            HashMap<String, Object> res = userService.login(js_code);
            System.out.println("js_code:" + js_code);
            return Result.success(res);
        } catch (Exception e) {
            return Result.error(String.valueOf(e));
        }
    }

    @RequestMapping(value = "/login-log", method = RequestMethod.GET)
    public Result checkLogin(@RequestHeader("Authorization") String jwt) {
        if (!Objects.equals(jwt, "")) {
            HashMap<String, Object> res = userService.checkLogin(jwt);
            if (res == null) {
                return Result.error("非法操作，用户不存在！");
            }
            return Result.success(res);
        } else {
            return Result.error("无JWT登录！");
        }
    }
}

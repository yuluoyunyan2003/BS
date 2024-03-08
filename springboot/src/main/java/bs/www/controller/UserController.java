//package bs.www.controller;
//
//import bs.www.service.GoodsService;
//import bs.www.service.UserService;
//import bs.www.vo.Result;
//import jakarta.servlet.http.HttpServletRequest;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Objects;
//
///**
// * @author w雨落云烟w
// * @date 2023/12/14 18:40
// * @description: 用户信息
// */
//@RestController
//@RequestMapping("/user")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private GoodsService goodsService;
//
//    /**
//     * @param js_code
//     * @return token+部分商品数据
//     * @description 用户登录时将token和部分首页商品数据一并返回，减少请求次数
//     */
//
//    @PostMapping("login-log")
//    public Result login(@RequestParam("js_code") String js_code) {
//        HashMap<String, Object> res = userService.login(js_code);
//        System.out.println("js_code:"+js_code);
//        return Result.success(res);
//    }
//
//    /**
//     * @param
//     * @return 如果JWT合法，则将JWT，homeData，cartData，ordersData一并返回
//     */
//    @RequestMapping(value = "/login-log",method = RequestMethod.GET)
//    public Result loginCheck(@RequestHeader("Authorization") String jwt) {
//        if (!Objects.equals(jwt, "")) {
//            HashMap<String, Object> res = userService.checkLogin(jwt);
//            if (res == null) {
//                return Result.error("非法操作，用户不存在！");
//            }
//            return Result.success(res);
//        } else {
//            return Result.error("无JWT登录！");
//        }
//    }
//
//    @PutMapping("/userInfo")
//    public Result updateUserInfo(@RequestHeader("Authorization") String jwt, @RequestParam("data") Object data) throws Exception {
//        HashMap<String, Object> res = userService.updateUserInfo(jwt, data);
//        return Result.success("成功！");
//    }
//
//    @PostMapping("/feedback")
//    public Result createFeedback(@RequestHeader("Authorization") String jwt, @RequestParam("param") String feedback) {
//        System.out.println(feedback);
//        userService.submitFeedback(jwt,feedback);
//        return Result.success(feedback);
//    }
//    @RequestMapping(value = "/goods/{name}",method = RequestMethod.GET)
//    public Result getGoodsByName(@PathVariable("name") String name){
//        List res = goodsService.getGoodsByName(name);
//        return Result.success(res);
//    }
//}

package bs.www.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author w雨落云烟w
 * @date 2023/12/23 16:06
 * @description: 拦截器
 */

@Component //将拦截器放到IOC容器里
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = request.getHeader("Authorization");
        try {
            //如果Redis没有该JWT就抛出异常
            if (Boolean.FALSE.equals(redisTemplate.hasKey(jwt))) {
                throw new RuntimeException();
            }
            //刷新token过期时间（待）
            System.out.println("200:" + jwt);
            System.out.println("200:" + request.getMethod());
            return true;
        } catch (Exception e) {
            System.out.println("401:" + jwt);
            System.out.println("401:" + request.getMethod());
            System.out.println("401:" + e);
            response.setStatus(401);
            return false;
        }
    }

}

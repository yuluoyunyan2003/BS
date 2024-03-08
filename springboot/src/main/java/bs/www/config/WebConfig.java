package bs.www.config;

import bs.www.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author w雨落云烟w
 * @date 2023/12/23 21:42
 * @description: 配置类，用于将拦截器注入到IOC容器
 */
@Configuration //将配置类注入IOC容器
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器
        registry.addInterceptor(loginInterceptor)
                //登录时的接口不拦截
                .excludePathPatterns("/user/login-log");
    }
}

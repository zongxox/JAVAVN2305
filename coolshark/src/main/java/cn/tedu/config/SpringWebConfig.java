package cn.tedu.config;

import cn.tedu.interceptor.TimeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration//指明當前類是一個配置類, 就是來替代之前Spring的配置文件
public class SpringWebConfig implements WebMvcConfigurer{
    //implements WebMvcConfigurer這個其實是相當於SpringMVC配置文件

    //配置SpringMVC攔截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor添加在註冊表當中
        //參數1:new TimeInterceptor()攔截器的類
        //參數2:addPathPatterns("/user/login")對誰進行攔截,對user/login路徑進行攔截
        registry.addInterceptor(new TimeInterceptor()).addPathPatterns("/user/login");
    }
}

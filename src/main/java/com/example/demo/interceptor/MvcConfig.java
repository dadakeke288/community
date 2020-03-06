//package com.example.demo.interceptor;
//
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//public class MvcConfig implements WebMvcConfigurer {
//    /**
//     *  视图跳转控制器
//     * 无业务逻辑的跳转 均可以以这种方法写在这里
//     * @param registry
//     */
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("wes");
//        registry.addViewController("/home").setViewName("wes");
//        registry.addViewController("/login").setViewName("login");
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        //告知系统static 当成 静态资源访问
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//    }
//}

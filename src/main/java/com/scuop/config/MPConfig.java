/*
 * @Author: SCUOP
 * @Date: 2022-12-17 01:01:32
 * @LastEditors: SCUOP
 * @LastEditTime: 2022-12-17 13:21:55
 * @Description: 图书管理系统
 */
package com.scuop.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
// import com.scuop.interceptors.AuthenticationInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MPConfig implements WebMvcConfigurer {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

    // 登录验证拦截器
    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    // registry.addInterceptor(authenticationInterceptor())
    // .addPathPatterns("/**");
    // }

    // @Bean
    // public AuthenticationInterceptor authenticationInterceptor() {
    // return new AuthenticationInterceptor();
    // }

}

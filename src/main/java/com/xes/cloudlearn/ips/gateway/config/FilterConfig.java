package com.xes.cloudlearn.ips.gateway.config;

import com.xes.cloudlearn.ips.gateway.filter.TokenAuthFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


/**
 *  初始化每一个过滤器, 设置过滤器起效时期和起效顺序
 */
@Component
public class FilterConfig {

    @Bean
    public TokenAuthFilter a() {
        TokenAuthFilter tokenAuthFilter = new TokenAuthFilter();
        return tokenAuthFilter;
    }


}

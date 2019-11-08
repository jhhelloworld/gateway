package com.xes.cloudlearn.ips.gateway.globalFilter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.reactive.result.view.RequestContext;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;


//全局过滤器，使用配置类形式，直接构造bean，使用注解完成Ordered接口功能,统计接口调用时间
@Configuration
public class GlobalGatewayFilter
{
    @Bean
    @Order(-1000)
    public GlobalFilter elapsedGlobalFilter()
    {
        return (exchange, chain) -> {
            //调用请求之前统计时间
            Long startTime = System.currentTimeMillis();
            return chain.filter(exchange).then().then(Mono.fromRunnable(() -> {
                //调用请求之后统计时间
                Long endTime = System.currentTimeMillis();
                System.out.println(
                    exchange.getRequest().getURI().getRawPath() + ", cost time : " + (endTime - startTime) + "ms");
            }));
        };
    }
}

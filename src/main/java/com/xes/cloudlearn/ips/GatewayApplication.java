package com.xes.cloudlearn.ips;

import com.xes.cloudlearn.ips.gateway.utils.AuthUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(GatewayApplication.class);
        springApplication.addListeners(new ApplicationListenerStarted());//增加监听器
        AuthUtil.init();
        SpringApplication.run(GatewayApplication.class, args);
    }


    private static class ApplicationListenerStarted
            implements ApplicationListener<ApplicationStartedEvent>
    {
        @Override
        public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent)
        {
            //权限初始化数据
            AuthUtil.init();
        }
    }



    /*地址限流*/

    @Bean
    @Primary
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostString());
    }


    /*用户限流*/
    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
    }


    /*接口限流*/
    @Bean
    KeyResolver apiKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }


}


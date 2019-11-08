package com.xes.cloudlearn.ips.gateway.globalFilter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.asn1.ocsp.ResponseData;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sun.rmi.runtime.Log;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * jh
 * 2019年11月06日  17：04
 */
@Component
public class WrapperResponseGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public int getOrder() {
        // -1 is response write filter, must be called before that
        return 2000;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        // 此处写死了，演示用，实际中需要采取配置的方式
        if (getIp(headers).equals("127.0.0.1")) {
            ServerHttpResponse response = exchange.getResponse();
            JSONObject json = new JSONObject();
            json.put("code","123");
            byte[] datas = json.toJSONString().getBytes();
            DataBuffer buffer = response.bufferFactory().wrap(datas);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        }));
    }

    // 这边从请求头中获取用户的实际IP,根据Nginx转发的请求头获取
    private String getIp(HttpHeaders headers) {
        return "127.0.0.2";
    }

}
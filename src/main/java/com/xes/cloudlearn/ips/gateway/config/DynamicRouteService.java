package com.xes.cloudlearn.ips.gateway.config;


import com.alibaba.fastjson.JSON;
import com.xes.cloudlearn.ips.gateway.db.RouteService;
import com.xes.cloudlearn.ips.gateway.vo.ZuulRouteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.URI;
import java.util.*;

/**
 * @author wuweifeng wrote on 2018/10/25.
 */
@Service
public class DynamicRouteService implements ApplicationEventPublisherAware {

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;
    @Autowired
    private RouteService routeService;

    //发起RefreshRoutesEvent事件，触发动态刷新（重新拉取路由配置信息）
    private void notifyChanged() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 全量替换动态路由（一个）
     * @param routeDefinition
     * @return
     */
    public String allReplace(String routeDefinition) {
        redisTemplate.opsForHash().put(RedisRouteDefinitionRepository.GATEWAY_ROUTES, "key", routeDefinition);
        notifyChanged();
        List<RouteDefinition> routeDefinitions = new ArrayList<>();
        redisTemplate.opsForHash().values(RedisRouteDefinitionRepository.GATEWAY_ROUTES).stream()
                .forEach(rd -> routeDefinitions.add(JSON.parseObject(rd.toString(), RouteDefinition.class)));
        System.out.println(JSON.toJSONString(routeDefinitions));
        return "success";
    }

    /**
     * 增加路由
     *
     */
    public String add(RouteDefinition definition) {
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        notifyChanged();
        return "success";
    }


    /**
     * 更新路由
     */
    public String update(RouteDefinition definition) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            return "update fail,not find route  routeId: " + definition.getId();
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            notifyChanged();
            return "success";
        } catch (Exception e) {
            return "update route  fail";
        }


    }

    /**
     * 删除路由
     *
     */
    public String delete(String id) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(id));

            notifyChanged();
            return "delete success";
        } catch (Exception e) {
            e.printStackTrace();
            return "delete fail";
        }

    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Resource
    private StringRedisTemplate redisTemplate;
    @PostConstruct
    public void main() {
        List<ZuulRouteVO> findRouteList = routeService.findRouteList();
        for(ZuulRouteVO zuulRouteVO:findRouteList){

            RouteDefinition definition = new RouteDefinition();
            definition.setId(zuulRouteVO.getId());
            URI uri = UriComponentsBuilder.fromHttpUrl(zuulRouteVO.getUrl()).build().toUri();
            definition.setUri(uri);
            //定义第一个断言
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setName("Path");
            Map<String, String> predicateParams = new HashMap<>(8);
            predicateParams.put("pattern", zuulRouteVO.getPath());
            predicate.setArgs(predicateParams);
            definition.setPredicates(Arrays.asList(predicate));
            System.out.println("definition:" + JSON.toJSONString(definition));
            redisTemplate.opsForHash().put(RedisRouteDefinitionRepository.GATEWAY_ROUTES, zuulRouteVO.getId(), JSON.toJSONString(definition));
        }





        RouteDefinition definition = new RouteDefinition();
        definition.setId("id");
        URI uri = UriComponentsBuilder.fromHttpUrl("http://127.0.0.1:8888/header").build().toUri();
        // URI uri = UriComponentsBuilder.fromHttpUrl("http://baidu.com").build().toUri();
        definition.setUri(uri);

        //定义第一个断言
        PredicateDefinition predicate = new PredicateDefinition();
        predicate.setName("Path");

        Map<String, String> predicateParams = new HashMap<>(8);
        predicateParams.put("pattern", "/jd");
        predicate.setArgs(predicateParams);

        //定义Filter
        FilterDefinition filter = new FilterDefinition();
        filter.setName("AddRequestHeader");
        Map<String, String> filterParams = new HashMap<>(8);
        //该_genkey_前缀是固定的，见org.springframework.cloud.gateway.support.NameUtils类
        filterParams.put("_genkey_0", "header");
        filterParams.put("_genkey_1", "addHeader");
        filter.setArgs(filterParams);

        FilterDefinition filter1 = new FilterDefinition();
        filter1.setName("AddRequestParameter");
        Map<String, String> filter1Params = new HashMap<>(8);
        filter1Params.put("_genkey_0", "param");
        filter1Params.put("_genkey_1", "addParam");
        filter1.setArgs(filter1Params);

        definition.setFilters(Arrays.asList(filter, filter1));
        definition.setPredicates(Arrays.asList(predicate));

        System.out.println("definition:" + JSON.toJSONString(definition));
        redisTemplate.opsForHash().put(RedisRouteDefinitionRepository.GATEWAY_ROUTES, "key", JSON.toJSONString(definition));
    }

    public String buildRouteDefinition(String id,String url,String path) {//path需要带/
        RouteDefinition definition = new RouteDefinition();
        definition.setId(id);
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build().toUri();
        // URI uri = UriComponentsBuilder.fromHttpUrl("http://baidu.com").build().toUri();
        definition.setUri(uri);

        //定义第一个断言
        PredicateDefinition predicate = new PredicateDefinition();
        predicate.setName("Path");

        Map<String, String> predicateParams = new HashMap<>(8);
        predicateParams.put("pattern", path);
        predicate.setArgs(predicateParams);

        //定义Filter
        FilterDefinition filter = new FilterDefinition();
        filter.setName("AddRequestHeader");
        Map<String, String> filterParams = new HashMap<>(8);
        //该_genkey_前缀是固定的，见org.springframework.cloud.gateway.support.NameUtils类
        filterParams.put("_genkey_0", "header");
        filterParams.put("_genkey_1", "addHeader");
        filter.setArgs(filterParams);

        FilterDefinition filter1 = new FilterDefinition();
        filter1.setName("AddRequestParameter");
        Map<String, String> filter1Params = new HashMap<>(8);
        filter1Params.put("_genkey_0", "param");
        filter1Params.put("_genkey_1", "addParam");
        filter1.setArgs(filter1Params);

        definition.setFilters(Arrays.asList(filter, filter1));
        definition.setPredicates(Arrays.asList(predicate));
        return JSON.toJSONString(definition);
    }
}


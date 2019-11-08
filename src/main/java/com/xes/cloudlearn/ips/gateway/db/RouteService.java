package com.xes.cloudlearn.ips.gateway.db;

import com.xes.cloudlearn.ips.gateway.vo.ZuulRouteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 路由表查询类
 *
 * @author lsy
 */
@Service
public class RouteService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询路由表
     * @return
     */
    public List<ZuulRouteVO> findRouteList() {
        String sql = "select zuulId id , path , serviceId , url , stripPrefix , retryable , sensitiveHeaders from gateway_route where is_delete = 0";
        List<ZuulRouteVO> routeList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ZuulRouteVO.class));
        return routeList;
    }
}

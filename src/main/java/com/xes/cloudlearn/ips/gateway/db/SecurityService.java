package com.xes.cloudlearn.ips.gateway.db;

import com.xes.cloudlearn.ips.gateway.vo.AuthModuleVO;
import com.xes.cloudlearn.ips.gateway.vo.SecurityKeyVO;
import com.xes.cloudlearn.ips.gateway.vo.SecurityModuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限表查询类
 *
 * @author lsy
 */
@Service
public class SecurityService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<SecurityKeyVO> findSecurityKeyList() {
        String sql = "select id , appId , appKey , is_delete isDelete from app_security_key where is_delete = 0";
        List<SecurityKeyVO> securityList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SecurityKeyVO.class));
        return securityList;
    }

    public List<SecurityModuleVO> findSecurityModuleList() {
        String sql = "select appId , zuulId from app_security_module where is_delete = 0";
        List<SecurityModuleVO> securityList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SecurityModuleVO.class));
        return securityList;
    }

    public List<AuthModuleVO> findAuthModuleList() {
        String sql = "select zuulId , path , whitePaths from auth_module where is_delete = 0";
        List<AuthModuleVO> authModuleList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AuthModuleVO.class));
        return authModuleList;
    }
}

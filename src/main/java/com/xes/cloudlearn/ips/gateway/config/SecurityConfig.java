package com.xes.cloudlearn.ips.gateway.config;

import com.xes.cloudlearn.ips.gateway.db.SecurityService;
import com.xes.cloudlearn.ips.gateway.vo.SecurityKeyVO;
import com.xes.cloudlearn.ips.gateway.vo.SecurityModuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限配置
 *
 * @author lsy
 */
@Component
public class SecurityConfig {

    @Autowired
    private SecurityService securityService;

    /** 三方调用秘钥表 */
    private Map<String, String> securityKeyMap;

    /** 三方调用模块表 */
    private Map<String, List<String>> securityModuleMap;

    @PostConstruct
    public void initSecurityMap() {
        List<SecurityKeyVO> securityKeyList = securityService.findSecurityKeyList();
        List<SecurityModuleVO> securityModuleList = securityService.findSecurityModuleList();
        securityKeyMap = securityKeyList.stream().collect(Collectors.toMap(SecurityKeyVO::getAppId, SecurityKeyVO::getAppKey));
        securityModuleMap  = securityModuleList.stream().collect(Collectors.groupingBy(SecurityModuleVO::getAppId, Collectors.mapping(SecurityModuleVO::getZuulId, Collectors.toList())));
    }

    public Map<String, String> getSecurityKeyMap() {
        return securityKeyMap;
    }

    public void setSecurityKeyMap(Map<String, String> securityKeyMap) {
        this.securityKeyMap = securityKeyMap;
    }

    public Map<String, List<String>> getSecurityModuleMap() {
        return securityModuleMap;
    }

    public void setSecurityModuleMap(Map<String, List<String>> securityModuleMap) {
        this.securityModuleMap = securityModuleMap;
    }
}

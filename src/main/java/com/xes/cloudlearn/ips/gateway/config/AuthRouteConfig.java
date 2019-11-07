package com.xes.cloudlearn.ips.gateway.config;

import com.xes.cloudlearn.ips.gateway.db.SecurityService;
import com.xes.cloudlearn.ips.gateway.vo.AuthModuleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 权限路由配置
 *
 * @author lsy
 */
@Component
public class AuthRouteConfig {

    @Autowired
    private SecurityService securityService;

    /** 需要权限验证的系统信息 */
    private Map<String, AuthRoute> authFilters;
    /** 内部调用令牌 */
    @Value("${icsToken}")
    private String icsToken;

    @PostConstruct
    public void initRouteMap() {
        authFilters = new HashMap<String, AuthRoute>();
        List<AuthModuleVO> authModuleList = securityService.findAuthModuleList();
        for(AuthModuleVO module : authModuleList) {
            AuthRoute authRoute = new AuthRoute();
            authRoute.setZuulId(module.getZuulId());
            authRoute.setPath(module.getPath());
            // 处理白名单格式
            String whitePaths = module.getWhitePaths();
            if(StringUtils.isNoneBlank(whitePaths)) {
                List<String> whitePathList = Arrays.stream(whitePaths.split(",")).map(p -> {
                    // 去除空白符
                    p = p.replaceAll("\\s+", "");
                    // 若有通配符*
                    p = p.replaceAll("\\*", "[^/].*");
                    return p;
                }).collect(Collectors.toList());
                authRoute.setWhitePaths(whitePathList);
            }
            authFilters.put(module.getZuulId(), authRoute);
        }
    }

    public Map<String, AuthRoute> getAuthFilters() {
        return authFilters;
    }

    public void setAuthFilters(Map<String, AuthRoute> authFilters) {
        this.authFilters = authFilters;
    }

    public String getIcsToken() {
        return icsToken;
    }

    public void setIcsToken(String icsToken) {
        this.icsToken = icsToken;
    }

    public static class AuthRoute {
        /** 路由id */
        private String zuulId;
        /** 路由路径 */
        private String path;
        /** 白名单 */
        private List<String> whitePaths;

        public String getZuulId() {
            return zuulId;
        }

        public void setZuulId(String zuulId) {
            this.zuulId = zuulId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public List<String> getWhitePaths() {
            return whitePaths;
        }

        public void setWhitePaths(List<String> whitePaths) {
            this.whitePaths = whitePaths;
        }
    }

}

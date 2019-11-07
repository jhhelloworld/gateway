package com.xes.cloudlearn.ips.gateway.controller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 登录校验相关接口
 *
 * @author lsy
 */
@FeignClient(name = "cloudlearn-ips-passport", path = "/passport")
public interface LoginService {

    /**
     * 校验用户token
     *
     * @param loginToken
     * @param icsToken
     * @param authToken
     * @param deviceId
     * @return
     */
    @RequestMapping(path = "/login/loginIn", method = RequestMethod.POST)
    Object loginValidate(@RequestParam(value = "loginToken") String loginToken,
                                    @RequestParam(value = "icsToken") String icsToken,
                                    @RequestParam(value = "authToken") String authToken,
                                    @RequestParam(value = "deviceId") String deviceId,
                                    @RequestParam(value = "appId") String appId,
                                    @RequestParam(value = "clientId") String clientId,
                                    @RequestParam(value = "userAgent") String userAgent);
}




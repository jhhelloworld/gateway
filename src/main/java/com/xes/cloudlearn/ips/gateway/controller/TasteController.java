package com.xes.cloudlearn.ips.gateway.controller;

import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 尝鲜控制器
 * @author zhangyt
 */
@RestController
public class TasteController {

    private static Logger logger = LoggerFactory.getLogger(TasteController.class);
    @Autowired
    private LoginService loginService;




    @RequestMapping(path = "/taste")
    public String taste() {
        logger.info("欢迎尝鲜");
        return loginService.loginValidate(null,null,null,null,null,null,null).toString();
    }

}

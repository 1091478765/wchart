package com.liulu123.xyz.wchart.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wx/")
public class WechartController {

    private static Logger logger = LoggerFactory.getLogger(WechartController.class);

    @RequestMapping("verifyWxToken")
    public String verifyWxToken(HttpServletRequest request){
        logger.info("进入验证方法");
        String msgSignature = request.getParameter("signature");
        String msgTimestamp = request.getParameter("timestamp");
        String msgNonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        logger.info("进入验证方法echostr为{}",echostr);
        return echostr;
    }

    @RequestMapping("auth")
    public void auth(@RequestParam(value = "code",required = false) String code){
        logger.info("进入授权认证方法,code为:{}",code);
    }
}

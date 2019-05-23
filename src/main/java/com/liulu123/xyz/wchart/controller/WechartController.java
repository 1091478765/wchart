package com.liulu123.xyz.wchart.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wx/")
public class WechartController {

    private static Logger logger = LoggerFactory.getLogger(WechartController.class);

    @Value("${wechart.appID}")
    private String appID;

    @Value("${wechart.appsecret}")
    private String appsecret;

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
        StringBuffer urlBuff = new StringBuffer("https://api.weixin.qq.com/sns/oauth2/access_token?");
        urlBuff
                .append("appid=")
                .append(appID)
                .append("&")
                .append("secret=")
                .append(appsecret)
                .append("&")
                .append("code=")
                .append(code)
                .append("&")
                .append("grant_type=authorization_code");
        RestTemplate restTemplate= new RestTemplate();
        logger.info("请求url为{}",urlBuff.toString());
        String forObject = restTemplate.getForObject(urlBuff.toString(), String.class);
        logger.info("返回结果为{}",forObject);
        //https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
    }
}

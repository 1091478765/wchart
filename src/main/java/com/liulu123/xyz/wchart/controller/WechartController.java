package com.liulu123.xyz.wchart.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liulu123.xyz.wchart.dto.WxAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

@RestController
@RequestMapping("/wx/")
public class WechartController {

    private static Logger logger = LoggerFactory.getLogger(WechartController.class);


    @Value("${wechat.appID}")
    private String appID;

    @Value("${wechat.appsecret}")
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
        WxAuth wxAuth = JSONObject.parseObject(forObject, WxAuth.class);
        logger.info("微信openid为：{}", wxAuth.getOpenid());

        //https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID
        StringBuffer authUrl = new StringBuffer("https://api.weixin.qq.com/sns/auth?access_token=");
        authUrl
                .append(wxAuth.getAccess_token())
                .append("&")
                .append("openid=")
                .append(wxAuth.getOpenid());
        String authResult = restTemplate.getForObject(authUrl.toString(), String.class);
        logger.info("验证结果为：{}", authResult);

        //刷新access_token
        //https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
        StringBuffer refreshUrl = new StringBuffer("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=");
        refreshUrl.append(appID)
                .append("&grant_type=refresh_token&refresh_token=")
                .append(wxAuth.getRefresh_token());
        logger.info("获取刷新token url为：{}", refreshUrl.toString());
        String refreshResultStr = restTemplate.getForObject(refreshUrl.toString(), String.class);
        WxAuth refreshResult = JSONObject.parseObject(refreshResultStr, WxAuth.class);
        logger.info("获取用户信息为：{}",refreshResultStr);

        StringBuffer userInfoUrl = new StringBuffer();
        userInfoUrl
                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                .append(refreshResult.getAccess_token())
                .append("&")
                .append("openid=")
                .append(refreshResult.getOpenid())
                .append("&lang=zh_CN");
        logger.info("获取用户信息url为：{}", userInfoUrl);
        String userInfoStr = restTemplate.getForObject(urlBuff.toString(), String.class);
        logger.info("获取用户信息为：{}",userInfoStr);



        //https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
    }

    public static void main(String[] args) {
        System.out.println(URLEncoder.encode("http://www.liulu123.xyz/wx/auth"));
        //https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx26db9832743573c3&redirect_uri=http%3A%2F%2Fwww.liulu123.xyz%2Fwx%2Fauth&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect

    }
}

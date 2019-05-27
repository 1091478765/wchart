package com.liulu123.xyz.wchart.controller;


import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wechat/")
public class WechatController {

    private static Logger logger = LoggerFactory.getLogger(WechartController.class);

    @Autowired
    private WxMpService wxMpService;

    @RequestMapping("getAuthUrl")
    public String verifyWxToken(HttpServletRequest request){
        logger.info("进入获取url接口");
        String url = "http://www.liulu123.xyz/wechat/receiveAccessCode";
        String s = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, "123");
        return s;
    }

    @RequestMapping("receiveAccessCode")
    public void auth(@RequestParam(value = "code",required = false) String code) throws Exception{
        logger.info("获取到用户code为:{}",code);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        logger.info("获取到用户信息为:{}",wxMpUser.toString());
    }
}

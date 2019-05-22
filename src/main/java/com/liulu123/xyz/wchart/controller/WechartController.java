package com.liulu123.xyz.wchart.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/")
public class WechartController {

    @GetMapping("/test")
    public String test5(){
        return "这是远程api服务";
    }
}

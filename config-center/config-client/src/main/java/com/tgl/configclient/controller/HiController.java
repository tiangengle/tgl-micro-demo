package com.tgl.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
public class HiController {

    /**
     * 配置文件中的属性 key
     */
    @Value("${foo}")
    private String foo;

    @RequestMapping("/hi")
    public String hi(){
        return foo;
    }
}

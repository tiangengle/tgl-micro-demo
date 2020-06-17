package com.tgl.eurekaclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("home")
public class HomeController {

    @Value("${server.port}")
    String port;

    @RequestMapping("hi")
    public String home(@RequestParam String name){
        return "hi " + name + ", I am from port:" +port;
    }
}

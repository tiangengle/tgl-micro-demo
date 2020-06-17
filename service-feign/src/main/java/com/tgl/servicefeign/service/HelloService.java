package com.tgl.servicefeign.service;

import com.tgl.servicefeign.service.hystrix.HelloServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-hi", fallback = HelloServiceHystrix.class)
public interface HelloService {

    @RequestMapping(value = "home/hi",method = RequestMethod.GET)
     String sayHiFromClientOne(@RequestParam(value = "name") String name);
}


package com.gsbsoft.evenmore.controller;

import com.gsbsoft.evenmore.service.HelloService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    private HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @RequestMapping("/")
    public String index(){
        helloService.getDBHelthy();
        return "index";
    }
}

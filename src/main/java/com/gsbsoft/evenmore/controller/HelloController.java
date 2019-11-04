package com.gsbsoft.evenmore.controller;


import com.gsbsoft.evenmore.service.HelloService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping("/sendEst")
    @ResponseBody
    public String sendEventAbout(HttpServletRequest req){
        helloService.insertEventPlan(req);
        return "200";
    }


}

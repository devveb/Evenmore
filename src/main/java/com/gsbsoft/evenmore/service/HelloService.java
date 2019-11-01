package com.gsbsoft.evenmore.service;


import com.gsbsoft.evenmore.mapper.ApiMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class HelloService {

    private ApiMapper apiMapper;


    public HelloService(ApiMapper apiMapper) {
        this.apiMapper = apiMapper;
    }


    public void getDBHelthy() {
        int status = apiMapper.getDBHealthy();
        System.out.println("DBH: "+status);
    }

    public void insertEventPlan(HttpServletRequest req) {
        Map map = new HashMap<>();
        map.put("name",req.getParameter("name"));
        map.put("email",req.getParameter("email"));
        map.put("phone",req.getParameter("phone"));
        map.put("msg",req.getParameter("msg"));
        apiMapper.insertEventPlan(map);
    }
}

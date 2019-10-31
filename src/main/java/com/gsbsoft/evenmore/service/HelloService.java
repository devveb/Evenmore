package com.gsbsoft.evenmore.service;

import com.gsbsoft.evenmore.mapper.ApiMapper;
import org.springframework.stereotype.Service;

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
}

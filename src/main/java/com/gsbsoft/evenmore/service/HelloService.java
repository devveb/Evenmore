package com.gsbsoft.evenmore.service;


import com.gsbsoft.evenmore.mapper.ApiMapper;
import com.gsbsoft.evenmore.util.ConfigConstants;
import com.gsbsoft.evenmore.util.MailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
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
        map.put("name",req.getParameter("n"));
        map.put("company",req.getParameter("c"));
        map.put("email",req.getParameter("e"));
        map.put("category",req.getParameter("g"));
        map.put("budget",req.getParameter("b"));
        map.put("msg",req.getParameter("m"));

        String html ="";
        html = html+"<p>이름: "+map.get("name")+"</p>";
        html = html+"<p>소속: "+map.get("company")+"</p>";
        html = html+"<p>이메일: "+map.get("email")+"</p>";
        html = html+"<p>문의내역: "+map.get("category")+"</p>";
        html = html+"<p>예산: "+map.get("budget")+"만원대</p>";
        html = html+"<p>문의사항: "+map.get("msg")+"</p>";


        MailSender ms = new MailSender();
        InternetAddress[] toList = new InternetAddress[2];
        try{
            InternetAddress to = new InternetAddress("taehyeong.kim@yap.net", "", ConfigConstants.MAIL_ENCODING);
            InternetAddress toto = new InternetAddress("mingjinshin@gmail.com", "", ConfigConstants.MAIL_ENCODING);
            toList[0]= to;
            toList[1]= toto;
            InternetAddress from = new InternetAddress("dev.taek@gmail.com", "관리자", ConfigConstants.MAIL_ENCODING);
            ms.send(null,from, toList, "문의드립니다.",html,null);
        }catch (Exception e){
            e.printStackTrace();
        }

        apiMapper.insertEventPlan(map);
    }

}

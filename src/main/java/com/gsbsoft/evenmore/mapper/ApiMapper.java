package com.gsbsoft.evenmore.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ApiMapper {

    int getDBHealthy();
    void insertEventPlan(Map amp);
}

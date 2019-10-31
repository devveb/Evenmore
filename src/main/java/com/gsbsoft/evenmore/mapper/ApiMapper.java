package com.gsbsoft.evenmore.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiMapper {

    int getDBHealthy();
}

package com.gis.monitor.mapper.basicinfo;


import com.gis.monitor.pojo.basicinfo.BI_USERS;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BI_USERS_MAPPER {
    List<BI_USERS> queryUerList();
    BI_USERS findUserByUserName(String USERNAME);
    BI_USERS login(String USERNAME,String PASSWORD);
}

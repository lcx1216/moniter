package com.gis.monitor.mapper.managegrade;


import com.gis.monitor.pojo.managegrade.MG_CP_DC_EQ;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MG_CP_DC_EQ_MAPPER {
    List<MG_CP_DC_EQ> selectList();
}




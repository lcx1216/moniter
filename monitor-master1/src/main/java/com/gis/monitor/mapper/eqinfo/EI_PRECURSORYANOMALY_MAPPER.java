package com.gis.monitor.mapper.eqinfo;

import com.gis.monitor.pojo.eqinfo.EI_PRECURSORYANOMALY;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EI_PRECURSORYANOMALY_MAPPER {

    List<EI_PRECURSORYANOMALY> getanormal();
}

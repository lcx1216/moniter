package com.gis.monitor.mapper.historyinfo;

import com.gis.monitor.pojo.historyinfo.HI_HISTORYEQ;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HI_HISTORYEQ_MAPPER {

    List<HI_HISTORYEQ> gethistoryData();
}

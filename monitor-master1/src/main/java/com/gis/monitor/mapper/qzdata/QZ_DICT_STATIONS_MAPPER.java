package com.gis.monitor.mapper.qzdata;


import com.gis.monitor.pojo.qzdata.QZ_DICT_STATIONS;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface QZ_DICT_STATIONS_MAPPER {
    List<QZ_DICT_STATIONS> getStations();

    QZ_DICT_STATIONS getstationPositionSql(String station_id);
}

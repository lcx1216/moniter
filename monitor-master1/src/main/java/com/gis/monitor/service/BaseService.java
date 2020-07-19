package com.gis.monitor.service;

import com.gis.monitor.mapper.qzdata.QZ_DICT_STATIONS_MAPPER;
import com.gis.monitor.pojo.qzdata.QZ_DICT_STATIONS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BaseService {

    @Autowired
    QZ_DICT_STATIONS_MAPPER qz_dict_stations_mapper;

    public List<QZ_DICT_STATIONS> getStations(){
        return qz_dict_stations_mapper.getStations();
    }
}

package com.gis.monitor.controller;

import com.gis.monitor.service.DDZ_PF_Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


//地电阻率观测资料监测预报效能评估

public class DDZ_PF {
   // MG_SCOREMANAGER_DDZ_SERVICE mg_scoremanager_ddz_service;
    DDZ_PF_Service ddz_pf_service;

    /*
    地电阻质量评价
 */

   // public class MG_PF_DDZ {
   public Map<String, Object> MG_PF_DDZ() {
       Map<String, Object> input = new HashMap();
       input = ddz_pf_service.MG_PF_DDZ(input);
       return input;
   }



   // }

    //地电阻率观测系统评估(改)
    public List<Object> OS_EarthResistivity(String station, String item, String point){
        List<Object> r = ddz_pf_service.OS_EarthResistivity(station,item,point);
        return r;

    }


    //地电阻查询(改)-跟踪应用评估
    public List<Object> DDZ_EarthResistivity(String station_id, String item_id, String point_id){
        List<Object> arr = ddz_pf_service.DDZ_EarthResistivity(station_id,item_id,point_id);
        return arr;
    }



}

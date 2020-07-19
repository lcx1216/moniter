package com.gis.monitor.mapper.basicinfo;


import com.gis.monitor.pojo.basicinfo.BI_SUB_EARTHRESISTIVITY;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BI_SUB_EARTHRESISTIVITY_MAPPER {
    List<BI_SUB_EARTHRESISTIVITY> getStationIdList(String itemId,String stationId,String pointId,Integer state);

    List<BI_SUB_EARTHRESISTIVITY> getAllEarthData(String station_id,String Ear_item_id,String Ear_point_id);

//    List<BI_SUB_EARTHRESISTIVITY> getEN_D_FAULT2STA();
//
//    List<BI_SUB_EARTHRESISTIVITY> getSEPSED();
//
//    List<BI_SUB_EARTHRESISTIVITY> getGCRNUM();
//
//    List<BI_SUB_EARTHRESISTIVITY> getIS_PFIPC();
//
//    List<BI_SUB_EARTHRESISTIVITY> getD_LRLD();
//
//    List<BI_SUB_EARTHRESISTIVITY> getIS_PSGL();
//
//    List<BI_SUB_EARTHRESISTIVITY> getCT();
//
//    List<BI_SUB_EARTHRESISTIVITY> getPDHRV();
//
//    List<BI_SUB_EARTHRESISTIVITY> getASIC();
//
//    List<BI_SUB_EARTHRESISTIVITY> getNPFEV();
//
//    List<BI_SUB_EARTHRESISTIVITY> getIPPFE();
//
//    List<BI_SUB_EARTHRESISTIVITY> getD_CERCF();
//
//    List<BI_SUB_EARTHRESISTIVITY> getMD_CETRC_BSIX();
//
//    List<BI_SUB_EARTHRESISTIVITY> getMD_CR();
//
//    List<BI_SUB_EARTHRESISTIVITY> getMD_HVT_THRKTFK();
//
//    List<BI_SUB_EARTHRESISTIVITY> getMD_HVT_F();
//
//    List<BI_SUB_EARTHRESISTIVITY> getMD_GWM();
//
//    List<BI_SUB_EARTHRESISTIVITY> getMD_TGW_THRTK();
//
//    List<BI_SUB_EARTHRESISTIVITY> getIS_ELF();
//
//    List<BI_SUB_EARTHRESISTIVITY> getECR();
//
//    List<BI_SUB_EARTHRESISTIVITY> getIS_LPD();
//
//    List<BI_SUB_EARTHRESISTIVITY> getIS_PREOB();
//
//    List<BI_SUB_EARTHRESISTIVITY> getIS_TEMOB();
//
//    List<BI_SUB_EARTHRESISTIVITY> getIS_ESC_HDPD();
//
//    List<BI_SUB_EARTHRESISTIVITY> getIS_FP_CD();






}

package com.gis.monitor.service;

import com.gis.monitor.mapper.basicinfo.BI_ITEMKEY_MAPPER;
import com.gis.monitor.mapper.eqinfo.EI_PRECURSORYANOMALY_MAPPER;
import com.gis.monitor.mapper.historyinfo.HI_HISTORYEQ_MAPPER;
import com.gis.monitor.mapper.managegrade.MG_SCOREMANAGER_DDZ_MAPPER;
import com.gis.monitor.mapper.basicinfo.BI_SUB_EARTHRESISTIVITY_MAPPER;
import com.gis.monitor.mapper.basicinfo.BI_MONTHLY_REPORT_MAPPER;
import com.gis.monitor.mapper.qzdata.QZ_DICT_STATIONS_MAPPER;
import com.gis.monitor.pojo.basicinfo.BI_ITEMKEY;
import com.gis.monitor.pojo.eqinfo.EI_PRECURSORYANOMALY;
import com.gis.monitor.pojo.historyinfo.HI_HISTORYEQ;
import com.gis.monitor.pojo.managegrade.MG_SCOREMANAGER_DDZ;
import com.gis.monitor.pojo.basicinfo.BI_SUB_EARTHRESISTIVITY;
import com.gis.monitor.pojo.basicinfo.BI_MONTHLY_REPORT;
import com.gis.monitor.pojo.qzdata.QZ_DICT_STATIONS;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


@Service
public class DDZ_PF_Service {


    private static final double EARTH_RADIUS = 6371393; // 平均半径,单位：m；不是赤道半径。赤道为6378左右

    @Autowired
    MG_SCOREMANAGER_DDZ_MAPPER mg_scoremanager_ddz_mapper;

    @Autowired
    BI_SUB_EARTHRESISTIVITY_MAPPER bi_sub_earthresistivity_mapper;

    @Autowired
    BI_MONTHLY_REPORT_MAPPER  bi_monthly_report_mapper;

    @Autowired
    BI_ITEMKEY_MAPPER bi_itemkey_mapper;

    @Autowired
    EI_PRECURSORYANOMALY_MAPPER ei_precursoryanomaly_mapper;

    @Autowired
    HI_HISTORYEQ_MAPPER hi_historyeq_mapper;

    @Autowired
    QZ_DICT_STATIONS_MAPPER qz_dict_stations_mapper;


//数据质量评价（20分）

    public Map<String, Object> MG_PF_DDZ(Map<String, Object> input) {

        //1.1.1  可靠性
        float kkx = (float) (Math.random() * (3 + 1) - 2) / 10;
        int kkx1 = 4;
        // =============================================================================================================
        // 1.1.2 稳定性：日均值资料连续变化规律（阶跃性变化），目前回溯3年；
        double wdx;
        if (input.get("稳定性") == null) {
            wdx = 999999;
        } else {
            wdx = (double) input.get("稳定性");
        }

        List<MG_SCOREMANAGER_DDZ> result1 = mg_scoremanager_ddz_mapper.getWDX();

        double wdx1;
        if (wdx == result1.get(0).getMIN()) {
            wdx1 = result1.get(0).getSCORE();
        } else if (wdx == result1.get(1).getMIN()) {
            wdx1 = result1.get(1).getSCORE();
        } else if (wdx <= result1.get(2).getMAX() && wdx >= result1.get(2).getMIN()) {
            wdx1 = result1.get(2).getSCORE();
        } else if (wdx > result1.get(3).getMIN() && wdx < 999999) {
            wdx1 = result1.get(3).getSCORE();
        } else {
            wdx = 999999;
            wdx1 = result1.get(3).getSCORE();
        }

        // =============================================================================================================
        // 1.1.3 连续性：连续缺数1个月以上计为缺数，目前回溯3年（4分）；
        double lxx;
        if (input.get("连续性") == null) {
            lxx = 999999;
        } else {
            lxx = (double) input.get("连续性");
        }

        List<MG_SCOREMANAGER_DDZ> result2 = mg_scoremanager_ddz_mapper.getLXX();

        double lxx1;
        if (lxx == result2.get(0).getMIN()) {
            lxx1 = result2.get(0).getSCORE();
        } else if (lxx == result2.get(3).getMAX()) {
            lxx1 = result2.get(3).getSCORE();
        } else if (lxx <= result2.get(1).getMAX() && lxx >= result2.get(1).getMIN()) {
            lxx1 = result2.get(1).getSCORE();
        } else if (lxx > result2.get(2).getMIN() && lxx > result2.get(2).getMIN()) {
            lxx1 = result2.get(2).getSCORE();
        } else {
            lxx = 999999;
            lxx1 = result2.get(3).getSCORE();
        }

        // =============================================================================================================
        // 1.1.4 观测精度：日均值资料与半年变周期以上成分均方差，目前回溯3年；
        double gcjd;
        if (input.get("观测精度") == null) {
            gcjd = 999999;
        } else {
            Object temp = input.get("观测精度");
            if (temp.equals("非数字")) {
                gcjd = 999999;
            } else {
                gcjd = (double) temp;
            }
        }
        List<MG_SCOREMANAGER_DDZ> result3 = mg_scoremanager_ddz_mapper.getGCJD();
        double gcjd1;
        if (gcjd == result3.get(3).getMAX()) {
            gcjd1 = result3.get(3).getSCORE();
        } else if (gcjd < result3.get(0).getMAX() && gcjd >= result3.get(0).getMIN()) {
            gcjd1 = result3.get(0).getSCORE();
        } else if (gcjd <= result3.get(1).getMAX() && gcjd >= result3.get(2).getMIN()) {
            gcjd1 = result3.get(2).getSCORE();
        } else if (gcjd > result3.get(2).getMIN()) {
            gcjd1 = result3.get(2).getSCORE();
        } else {
            gcjd = 999999;
            gcjd1 = result3.get(2).getSCORE();
        }


        // =============================================================================================================
        // 1.1.5 年变特征，目前回溯5年（3分）；
        double nbtz;
        if (input.get("年变特征") == null) {
            nbtz = 999999;
        } else {
            Object temp = input.get("年变特征");
            if (temp.equals("非数字")) {
                nbtz = 999999;
            } else {
                nbtz = (double) temp;
            }
        }
        List<MG_SCOREMANAGER_DDZ> result4 = mg_scoremanager_ddz_mapper.getNBTZ();
        double nbtz1 = 0;
        if (result4.get(0).getMAX() != null && nbtz > result4.get(0).getMAX() && nbtz < 999999) {
            nbtz1 = result4.get(0).getSCORE();
        } else if (nbtz >= result4.get(1).getMIN() && nbtz <= result4.get(1).getMAX()) {
            nbtz1 = result4.get(1).getSCORE();
        } else if (nbtz < result4.get(2).getMAX()) {
            nbtz1 = result4.get(2).getSCORE();
        } else {
            nbtz = 999999;
            nbtz1 = result4.get(2).getSCORE();
        }

        // =============================================================================================================
        // 1.1.6 观测长度（2分）；
        double gccd;
        if (input.get("观测长度") == null) {
            gccd = 999999;
        } else {
            Object temp = input.get("观测长度");
            if (temp.equals("非数字")) {
                gccd = 999999;
            } else {
                gccd = (double) temp;
            }
        }
        List<MG_SCOREMANAGER_DDZ> result5 = mg_scoremanager_ddz_mapper.getGCCD();

        double gccd1 = 0;
        if (gccd > result5.get(0).getMIN() && gccd < 999999) {
            gccd1 = result5.get(0).getSCORE();
        } else if (gccd >= result5.get(1).getMIN() && gccd <= result5.get(1).getMAX()) {
            gccd1 = result5.get(1).getSCORE();
        } else if (gccd < result5.get(2).getMAX()) {
            gccd1 = result5.get(2).getSCORE();
        } else {
            gccd = 999999;
            gccd1 = result5.get(2).getSCORE();
        }

        // =============================================================================================================
        //        //2 辅助观测资料（8分）
        //        //2.1.1 潜水位观测连续性：连续缺数1个月以上计为缺数，目前回溯3年
        double qswgclxx = 0;
        if (input.get("潜水位观测连续性") == null) {
            qswgclxx = 999999;
        } else {
            Object temp = input.get("潜水位观测连续性");
            if (temp.equals("999,999.00")) {
                qswgclxx = 999999;
            } else {
                qswgclxx = (double) temp;
            }
        }
        List<MG_SCOREMANAGER_DDZ> result6 = mg_scoremanager_ddz_mapper.getQSWGCLXX();
        double qswgclxx1 = 0;
        if (qswgclxx == 0) {
            qswgclxx = result6.get(0).getSCORE();
        } else if (qswgclxx == 999999) {
            qswgclxx1 = result6.get(3).getSCORE();
        } else if ((qswgclxx * -1) < result6.get(3).getMAX() && qswgclxx > 0) {
            qswgclxx1 = result6.get(3).getSCORE();
        } else if ((qswgclxx * -1) > result6.get(1).getMIN() && (qswgclxx * -1) <= result6.get(1).getMAX()) {
            qswgclxx1 = result6.get(1).getSCORE();
        } else if (qswgclxx * -1 > result6.get(2).getMIN() && qswgclxx * -1 <= result6.get(2).getMAX()) {
            qswgclxx1 = result6.get(2).getSCORE();
        } else {
            qswgclxx = 999999;
            qswgclxx1 = result6.get(2).getSCORE();
        }

        // =============================================================================================================


//        //2.1.2 潜水位观测长度（1分）；
        double qswgccd = 0;
        if (input.get("潜水位观测长度") == null) {
            qswgccd = 999999;
        } else {
            Object temp = input.get("潜水位观测长度");
            if (temp.equals("999,999.00")) {
                qswgccd = 999999;
            } else {
                qswgccd = (double) temp;
            }
        }
        double qswgccd1 = 0;
        List<MG_SCOREMANAGER_DDZ> result7 = mg_scoremanager_ddz_mapper.getQSWGCCD();
        if (qswgccd > result7.get(0).getMIN() && qswgccd <= 999999) {
            qswgccd1 = result7.get(0).getSCORE();
        } else if (qswgccd >= result7.get(1).getMIN() && qswgccd <= result7.get(1).getMAX()) {
            qswgccd1 = result7.get(1).getSCORE();
        } else if (qswgccd < result7.get(2).getMAX()) {
            qswgccd1 = result7.get(2).getSCORE();
        } else {
            qswgccd = 999999;
            qswgccd1 = result7.get(2).getSCORE();
        }
//

        // =============================================================================================================
        //2.2.1 降雨量观测连续性：连续缺数1个月以上计为缺数，目前回溯3年
        mg_scoremanager_ddz_mapper.getJYLGCLXX();
        double jylgclxx = 0;
        if (input.get("降雨量观测连续性") == null) {
            jylgclxx = 999999;
        } else {
            Object temp = input.get("降雨量观测连续性");
            if (temp.equals("999,999.00")) {
                jylgclxx = 999999;
            } else {
                jylgclxx = (double) temp;
            }
        }
        List<MG_SCOREMANAGER_DDZ> result8 = mg_scoremanager_ddz_mapper.getJYLGCLXX();
        double jylgclxx1 = 0;
        if (jylgclxx == 0) {
            jylgclxx1 = result8.get(0).getSCORE();
        } else if ((jylgclxx * -1) > result8.get(1).getMIN() && (jylgclxx * -1) <= result8.get(1).getMAX()) {
            jylgclxx1 = result8.get(1).getSCORE();
        } else if ((jylgclxx * -1) > result8.get(2).getMIN() && (jylgclxx * -1) <= result8.get(2).getMAX()) {
            jylgclxx1 = result8.get(2).getSCORE();
        } else if ((jylgclxx * -1) > 0 && (jylgclxx * -1) <= result8.get(2).getMAX()) {
            jylgclxx1 = result8.get(3).getSCORE();
        } else {
            jylgclxx = 999999;
            jylgclxx1 = result8.get(3).getSCORE();
        }

//

        // =============================================================================================================
        //2.2.2 降雨量观测长度（1分）；
        mg_scoremanager_ddz_mapper.getJYLGCCD();
        double jylgccd = 0;
        if (input.get("降雨量观测长度") == null) {
            jylgccd = 999999;
        } else {
            Object temp = input.get("降雨量观测长度");
            if (temp.equals("999,999.00")) {
                jylgccd = 999999;
            } else {
                jylgccd = (double) temp;
            }
        }
        List<MG_SCOREMANAGER_DDZ> result9 = mg_scoremanager_ddz_mapper.getJYLGCCD();
        double jylgccd1 = 0;
        if (jylgccd > result9.get(0).getMIN() && jylgccd < 999999) {
            jylgccd1 = result9.get(0).getSCORE();
        } else if (jylgccd >= result9.get(1).getMIN() && jylgccd <= result9.get(1).getMAX()) {
            jylgccd1 = result9.get(1).getSCORE();
        } else if (jylgccd < result9.get(2).getMAX()) {
            jylgccd1 = result9.get(2).getSCORE();
        } else {
            jylgccd = 999999;
            jylgccd1 = result9.get(2).getSCORE();
        }


        // =============================================================================================================
        //2.3温度观测资料
        //2.3.1温度观测连续性
        mg_scoremanager_ddz_mapper.getWDGCLXX();
        double wdgclxx = 0;
        if (input.get("温度观测连续性") == null) {
            wdgclxx = 999999;
        } else {
            Object temp = input.get("温度观测连续性");
            if (temp.equals("999,999.00")) {
                wdgclxx = 999999;
            } else {
                wdgclxx = (double) temp;
            }
        }
        List<MG_SCOREMANAGER_DDZ> result10 = mg_scoremanager_ddz_mapper.getWDGCLXX();
        double wdgclxx1 = 0;
        if (wdgclxx == 0) {
            wdgclxx1 = result10.get(0).getSCORE();
        } else if (((wdgclxx * -1) / 12) >= result10.get(1).getMIN() && ((wdgclxx * -1) / 12) < result10.get(1).getMAX()) {
            wdgclxx1 = result10.get(1).getSCORE();
        } else if (((wdgclxx * -1) / 12) >= result10.get(2).getMIN() && ((wdgclxx * -1) / 12) < result10.get(2).getMAX()) {
            wdgclxx1 = result10.get(2).getSCORE();
        } else if (((wdgclxx * -1) / 12) > 0 && ((wdgclxx * -1) / 12) < result10.get(3).getMAX()) {
            wdgclxx1 = result10.get(3).getSCORE();
        } else {
            wdgclxx = 999999;
            wdgclxx1 = result10.get(3).getSCORE();
        }


        // =============================================================================================================
        //2.3.2温度观测长度
        mg_scoremanager_ddz_mapper.getWDGCCD();
        double wdgccd = 0;
        if (input.get("温度观测长度") == null) {
            wdgccd = 999999;
        } else {
            Object temp = input.get("温度观测长度");
            if (temp.equals("999,999.00")) {
                wdgccd = 999999;
            } else {
                wdgccd = (double) temp;
            }
        }
        List<MG_SCOREMANAGER_DDZ> result11 = mg_scoremanager_ddz_mapper.getWDGCCD();
        double wdgccd1 = 0;
        if (wdgccd > result11.get(0).getMIN() && wdgccd < 999999) {
            wdgccd1 = result11.get(0).getSCORE();
        } else if (wdgccd >= result11.get(1).getMIN() && wdgccd <= result11.get(1).getMAX()) {
            wdgccd1 = result11.get(1).getSCORE();
        } else if (wdgccd > 0 && wdgccd < result11.get(2).getMAX()) {
            wdgccd1 = result11.get(2).getSCORE();
        } else {
            wdgccd = 999999;
            wdgccd1 = result11.get(2).getSCORE();
        }


        input.put("可靠性", kkx);
        input.put("稳定性", wdx);
        input.put("连续性", lxx);
        input.put("观测精度", gcjd);
        input.put("年变特征", nbtz);
        input.put("观测长度", gccd);
        input.put("潜水位观测连续性", qswgclxx);
        input.put("潜水位观测长度", qswgccd);
        input.put("降雨量观测连续性", jylgclxx);
        input.put("温度观测长度", jylgccd);
        input.put("可靠性_grade", kkx1);
        input.put("稳定性_grade", wdx1);
        input.put("连续性_grade", lxx1);
        input.put("观测精度_grade", gcjd1);
        input.put("年变特征_grade", nbtz1);
        input.put("观测长度_grade", gccd1);
        input.put("潜水位观测连续性_grade", qswgclxx1);
        input.put("潜水位观测长度_grade", qswgccd1);
        input.put("降雨量观测连续性_grade", jylgclxx1);
        input.put("降雨量观测长度_grade", jylgccd1);
        input.put("温度观测连续性_grade", wdgclxx1);
        input.put("温度观测长度_grade", wdgccd1);
        input.put("totalgrade", (int) (kkx1 + wdx1 + lxx1 + gcjd1 + nbtz1 + gccd1 + qswgclxx1 + qswgccd1 + jylgclxx1 + jylgccd1 + wdgclxx1 + wdgccd1));
        return input;
    }


    //地电阻率观测系统评估(改)



    public List<Object> OS_EarthResistivity(String station, String item, String point) {
        String station_id = station;
        String point_id = point;
        String item_id = item.substring(0, 3);
        List<BI_SUB_EARTHRESISTIVITY> sub_info = bi_sub_earthresistivity_mapper.getStationIdList(item_id, station, point_id, 1);

        List<MG_SCOREMANAGER_DDZ> mg_info = mg_scoremanager_ddz_mapper.getAll();


        //地质地貌条件（1分）
        Double grade_dzdm = null;
        //1.1观测场地宜选在地震活动带内或在活动断裂带附近
        Double grade_dzdm1 = null;

        if (Double.valueOf(sub_info.get(0).getEN_D_FAULT2STA()) > 0 && Double.valueOf(sub_info.get(0).getEN_D_FAULT2STA()) < Double.valueOf(mg_info.get(37).getMAX()) && sub_info.get(0).getEN_D_FAULT2STA() != null) {
            grade_dzdm1 = Double.valueOf(mg_info.get(37).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getEN_D_FAULT2STA()) >= Double.valueOf(mg_info.get(38).getMIN()) && Double.valueOf(sub_info.get(0).getEN_D_FAULT2STA()) < Double.valueOf(mg_info.get(38).getMAX())) {
            grade_dzdm1 = Double.valueOf(mg_info.get(38).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getEN_D_FAULT2STA()) >= Double.valueOf(mg_info.get(39).getMIN()) && Double.valueOf(sub_info.get(0).getEN_D_FAULT2STA()) < Double.valueOf(mg_info.get(39).getMAX())) {
            grade_dzdm1 = Double.valueOf(mg_info.get(39).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getEN_D_FAULT2STA()) >= Double.valueOf(mg_info.get(40).getMIN()) && Double.valueOf(sub_info.get(0).getEN_D_FAULT2STA()) < 999999) {
            grade_dzdm1 = Double.valueOf(mg_info.get(40).getSCORE());
        } else {
            sub_info.get(0).setEN_D_FAULT2STA(String.valueOf(999999));
            grade_dzdm1 = Double.valueOf(mg_info.get(40).getSCORE());
        }

        //1.2布极区应地形开阔、地势平坦，地形高差不宜大于电极间距的5％
        Double grade_dzdm2 = null;
        if (Double.valueOf(sub_info.get(0).getSEPSED()) <= 1) {
            if ((Double.valueOf(sub_info.get(0).getSEPSED()) * 100) >= 0 && (Double.valueOf(sub_info.get(0).getSEPSED())) * 100 <= Double.valueOf(mg_info.get(41).getMAX()) && sub_info.get(0).getSEPSED() != null) {
                grade_dzdm2 = Double.valueOf(mg_info.get(41).getSCORE());
            } else if ((Double.valueOf(sub_info.get(0).getSEPSED()) * 100) >= Double.valueOf(mg_info.get(43).getMIN()) && (Double.valueOf(sub_info.get(0).getSEPSED())) * 100 <= Double.valueOf(mg_info.get(43).getMAX())) {
                grade_dzdm2 = Double.valueOf(mg_info.get(43).getSCORE());
            } else if ((Double.valueOf(sub_info.get(0).getSEPSED()) * 100) >= Double.valueOf(mg_info.get(42).getMIN()) && (Double.valueOf(sub_info.get(0).getSEPSED())) * 100 < 999999) {
                grade_dzdm2 = Double.valueOf(mg_info.get(42).getSCORE());
            } else {
                sub_info.get(0).setSEPSED(String.valueOf(999999));
                grade_dzdm2 = Double.valueOf(mg_info.get(42).getSCORE());
            }
        } else {
            if (Double.valueOf(sub_info.get(0).getSEPSED()) >= 0 && Double.valueOf(sub_info.get(0).getSEPSED()) <= Double.valueOf(mg_info.get(41).getMAX()) && sub_info.get(0).getSEPSED() != null) {
                grade_dzdm2 = Double.valueOf(mg_info.get(41).getSCORE());
            } else if (Double.valueOf(sub_info.get(0).getSEPSED()) >= Double.valueOf(mg_info.get(43).getMIN()) && Double.valueOf(sub_info.get(0).getSEPSED()) <= Double.valueOf(mg_info.get(43).getMAX())) {
                grade_dzdm2 = Double.valueOf(mg_info.get(43).getSCORE());
            } else if (Double.valueOf(sub_info.get(0).getSEPSED()) >= Double.valueOf(mg_info.get(42).getMIN()) && Double.valueOf(sub_info.get(0).getSEPSED()) < 999999) {
                grade_dzdm2 = Double.valueOf(mg_info.get(42).getSCORE());
            } else {
                sub_info.get(0).setSEPSED(String.valueOf(999999));
                grade_dzdm2 = Double.valueOf(mg_info.get(42).getSCORE());
            }
        }

        //1.3布极区内不应有沟壑、崖坎、河流等
        Double grade_dzdm3 = null;
        if (Double.valueOf(sub_info.get(0).getGCRNUM()) >= Double.valueOf(mg_info.get(44).getMIN()) && sub_info.get(0).getGCRNUM() != null && Double.valueOf(sub_info.get(0).getGCRNUM()) != 999999) {
            grade_dzdm3 = Double.valueOf(mg_info.get(44).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getGCRNUM()) >= Double.valueOf(mg_info.get(45).getMIN()) && Double.valueOf(sub_info.get(0).getGCRNUM()) <= Double.valueOf(mg_info.get(45).getMAX())) {
            grade_dzdm3 = Double.valueOf(mg_info.get(45).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getGCRNUM()) == 0 && sub_info.get(0).getGCRNUM() != null) {
            grade_dzdm3 = Double.valueOf(mg_info.get(46).getSCORE());
        } else {
            sub_info.get(0).setGCRNUM(String.valueOf(999999));
            ;
            grade_dzdm3 = Double.valueOf(mg_info.get(42).getSCORE());
        }

        grade_dzdm = grade_dzdm1 + grade_dzdm2 + grade_dzdm3;

        //水文地质条件
        Double grade_swdz = null;
        //2.1布极区不宜选在抽水漏斗区内
        Double grade_swdz1 = null;
        if (sub_info.get(0).getIS_PFIPC() == String.valueOf(mg_info.get(23).getTYPE())) {
            grade_swdz1 = Double.valueOf(mg_info.get(23).getSCORE());
        } else if (sub_info.get(0).getIS_PFIPC() == String.valueOf(mg_info.get(22).getTYPE())) {
            grade_swdz1 = Double.valueOf(mg_info.get(22).getSCORE());
        } else {
            sub_info.get(0).setIS_PFIPC(String.valueOf(999999));
            ;
            grade_swdz1 = Double.valueOf(mg_info.get(22).getSCORE());
        }
        //2.2布极区边缘避开大型水库、湖泊的距离不宜小于3km
        Double grade_swdz2 = null;
        if (Double.valueOf(sub_info.get(0).getD_LRLD()) <= Double.valueOf(mg_info.get(24).getMAX()) && sub_info.get(0).getD_LRLD() != null) {
            grade_swdz2 = Double.valueOf(mg_info.get(24).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getD_LRLD()) > Double.valueOf(mg_info.get(27).getMIN()) && Double.valueOf(sub_info.get(0).getD_LRLD()) <= Double.valueOf(mg_info.get(27).getMAX())) {
            grade_swdz2 = Double.valueOf(mg_info.get(27).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getD_LRLD()) > Double.valueOf(mg_info.get(26).getMIN()) && Double.valueOf(sub_info.get(0).getD_LRLD()) <= Double.valueOf(mg_info.get(26).getMAX())) {
            grade_swdz2 = Double.valueOf(mg_info.get(26).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getD_LRLD()) > 0 && Double.valueOf(sub_info.get(0).getD_LRLD()) <= Double.valueOf(mg_info.get(25).getMAX())) {
            grade_swdz2 = Double.valueOf(mg_info.get(25).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getD_LRLD()) == 0) {
            grade_swdz2 = Double.valueOf(mg_info.get(25).getSCORE());
        } else {
            sub_info.get(0).setD_LRLD(String.valueOf(999999));
            ;
            grade_swdz2 = Double.valueOf(mg_info.get(25).getSCORE());
        }
        grade_swdz = grade_swdz1 + grade_swdz2;

        //岩性条件
        Double grade_yx = null;
        //3.1布极区表层不应为卵石层、砾石层
        Double grade_yx1 = null;
        if (sub_info.get(0).getIS_PSGL() == String.valueOf(mg_info.get(21).getTYPE())) {
            grade_yx1 = Double.valueOf(mg_info.get(21).getSCORE());
        } else if (sub_info.get(0).getIS_PSGL() == String.valueOf(mg_info.get(20).getTYPE())) {
            grade_yx1 = Double.valueOf(mg_info.get(20).getSCORE());
        } else {
            sub_info.get(0).setIS_PSGL(String.valueOf(999999));
            ;
            grade_yx1 = Double.valueOf(mg_info.get(20).getSCORE());
        }
        //3.2土层、砂土层及卵石、砾石组成的第四系覆盖层的厚度不宜超过200m
        Double grade_yx2 = null;
        if (Double.valueOf(sub_info.get(0).getCT()) < Double.valueOf(mg_info.get(16).getMAX()) && Double.valueOf(sub_info.get(0).getCT()) > 0) {
            grade_yx2 = Double.valueOf(mg_info.get(16).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getCT()) == 0) {
            grade_yx2 = Double.valueOf(mg_info.get(16).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getCT()) >= Double.valueOf(mg_info.get(19).getMIN()) && Double.valueOf(sub_info.get(0).getCT()) < Double.valueOf(mg_info.get(19).getMAX())) {
            grade_yx2 = Double.valueOf(mg_info.get(19).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getCT()) >= Double.valueOf(mg_info.get(18).getMIN()) && Double.valueOf(sub_info.get(0).getCT()) < Double.valueOf(mg_info.get(18).getMAX())) {
            grade_yx2 = Double.valueOf(mg_info.get(18).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getCT()) >= Double.valueOf(mg_info.get(17).getMIN()) && Double.valueOf(sub_info.get(0).getCT()) <= 999999) {
            grade_yx2 = Double.valueOf(mg_info.get(17).getSCORE());
        } else {
            sub_info.get(0).setCT(String.valueOf(999999));
            ;
            grade_yx2 = Double.valueOf(mg_info.get(17).getSCORE());
        }
        grade_yx = grade_yx1 + grade_yx2;

        //电性结构
        Double grade_dxjg = null;
        //4.1供电电极距100m时测得的视电阻率宜在10 Ωm-50 Ωm
        Double grade_dxjg1 = null;
        if (Double.valueOf(sub_info.get(0).getPDHRV()) < Double.valueOf(mg_info.get(28).getMAX()) && sub_info.get(0).getPDHRV() != null) {
            grade_dxjg1 = Double.valueOf(mg_info.get(28).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getPDHRV()) >= Double.valueOf(mg_info.get(32).getMIN()) && Double.valueOf(sub_info.get(0).getPDHRV()) < Double.valueOf(mg_info.get(32).getMAX())) {
            grade_dxjg1 = Double.valueOf(mg_info.get(32).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getPDHRV()) >= Double.valueOf(mg_info.get(29).getMIN()) && Double.valueOf(sub_info.get(0).getPDHRV()) < Double.valueOf(mg_info.get(29).getMAX())) {
            grade_dxjg1 = Double.valueOf(mg_info.get(29).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getPDHRV()) >= Double.valueOf(mg_info.get(31).getMIN()) && Double.valueOf(sub_info.get(0).getPDHRV()) < Double.valueOf(mg_info.get(31).getMAX())) {
            grade_dxjg1 = Double.valueOf(mg_info.get(31).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getPDHRV()) >= Double.valueOf(mg_info.get(30).getMIN()) && Double.valueOf(sub_info.get(0).getPDHRV()) <= 999999) {
            grade_dxjg1 = Double.valueOf(mg_info.get(30).getSCORE());
        } else {
            sub_info.get(0).setPDHRV(String.valueOf(999999));
            ;
            grade_dxjg1 = Double.valueOf(mg_info.get(30).getSCORE());
        }

        //4.2表层影响系数S的绝对值宜小于0.2
        Double grade_dxjg2 = null;
        if (Double.valueOf(sub_info.get(0).getASIC()) < Double.valueOf(mg_info.get(33).getMAX()) && sub_info.get(0).getASIC() != null) {
            grade_dxjg2 = Double.valueOf(mg_info.get(33).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getASIC()) >= Double.valueOf(mg_info.get(36).getMIN()) && Double.valueOf(sub_info.get(0).getASIC()) < Double.valueOf(mg_info.get(36).getMAX())) {
            grade_dxjg2 = Double.valueOf(mg_info.get(36).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getASIC()) >= Double.valueOf(mg_info.get(35).getMIN()) && Double.valueOf(sub_info.get(0).getASIC()) < Double.valueOf(mg_info.get(35).getMAX())) {
            grade_dxjg2 = Double.valueOf(mg_info.get(35).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getASIC()) >= Double.valueOf(mg_info.get(34).getMIN()) && Double.valueOf(sub_info.get(0).getASIC()) < 999999) {
            grade_dxjg2 = Double.valueOf(mg_info.get(34).getSCORE());
        } else {
            sub_info.get(0).setASIC(String.valueOf(999999));
            ;
            grade_dxjg2 = Double.valueOf(mg_info.get(34).getSCORE());
        }
        grade_dxjg = grade_dxjg1 + grade_dxjg2;

        //场地电磁环境
        Double grade_cddc = null;
        //5.1非工频人工电磁源在测量极间产生的干扰电压不大于45 μV
        Double grade_cddc1 = null;
        if (Double.valueOf(sub_info.get(0).getNPFEV()) <= Double.valueOf(mg_info.get(50).getMAX()) && Double.valueOf(sub_info.get(0).getNPFEV()) != null) {
            Double.valueOf(mg_info.get(50).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getNPFEV()) > Double.valueOf(mg_info.get(51).getMIN()) && Double.valueOf(sub_info.get(0).getNPFEV()) <= Double.valueOf(mg_info.get(51).getMAX())) {
            Double.valueOf(mg_info.get(51).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getNPFEV()) > Double.valueOf(mg_info.get(52).getMIN()) && Double.valueOf(sub_info.get(0).getNPFEV()) <= Double.valueOf(mg_info.get(52).getMAX())) {
            Double.valueOf(mg_info.get(52).getSCORE());
        } else {
            sub_info.get(0).setNPFEV(String.valueOf(999999));
            ;
            grade_cddc1 = Double.valueOf(mg_info.get(52).getSCORE());
        }

        //5.2工频人工电磁源在测量极间产生的干扰峰值电压 不大于500 mV
        Double grade_cddc2 = null;
        if (Double.valueOf(sub_info.get(0).getIPPFE()) <= Double.valueOf(mg_info.get(47).getMAX()) && sub_info.get(0).getIPPFE() != null) {
            grade_cddc2 = Double.valueOf(mg_info.get(47).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getIPPFE()) > Double.valueOf(mg_info.get(48).getMIN()) && Double.valueOf(sub_info.get(0).getIPPFE()) <= Double.valueOf(mg_info.get(48).getMAX())) {
            grade_cddc2 = Double.valueOf(mg_info.get(48).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getIPPFE()) > Double.valueOf(mg_info.get(49).getMIN()) && Double.valueOf(sub_info.get(0).getIPPFE()) <= 999999) {
            grade_cddc2 = Double.valueOf(mg_info.get(49).getSCORE());
        } else {
            sub_info.get(0).setIPPFE(String.valueOf(999999));
            ;
            grade_cddc2 = Double.valueOf(mg_info.get(49).getSCORE());
        }
        grade_cddc = grade_cddc1 + grade_cddc2;

        //测区观测环境
        Double grade_cqgchj = null;
        //6.1城市电气化地（城）铁轨道与观测场地中心的距离不小于30 km
        Double grade_cqgchj1 = null;
        if (Double.valueOf(sub_info.get(0).getD_CERCF()) < Double.valueOf(mg_info.get(61).getMAX()) && sub_info.get(0).getD_CERCF() != null) {
            grade_cqgchj1 = Double.valueOf(mg_info.get(61).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getD_CERCF()) > Double.valueOf(mg_info.get(64).getMIN()) && Double.valueOf(sub_info.get(0).getD_CERCF()) <= Double.valueOf(mg_info.get(64).getMAX())) {
            grade_cqgchj1 = Double.valueOf(mg_info.get(64).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getD_CERCF()) >= Double.valueOf(mg_info.get(62).getMIN()) && Double.valueOf(sub_info.get(0).getD_CERCF()) <= Double.valueOf(mg_info.get(62).getMAX())) {
            grade_cqgchj1 = Double.valueOf(mg_info.get(62).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getD_CERCF()) > Double.valueOf(mg_info.get(63).getMIN())) {
            grade_cqgchj1 = Double.valueOf(mg_info.get(63).getSCORE());
        } else {
            sub_info.get(0).setD_CERCF(String.valueOf(999999));
            grade_cqgchj1 = Double.valueOf(mg_info.get(63).getSCORE());
        }

        //6.2电气化铁路运输系统在牵引功率不超过6000kW的条件下，轨道与任意一个测向中心点的距离不小于5 km
        Double grade_cqgchj2 = null;
        if (Double.valueOf(sub_info.get(0).getMD_CETRC_BSIX()) < Double.valueOf(mg_info.get(57).getMAX()) && sub_info.get(0).getMD_CETRC_BSIX() != null) {
            grade_cqgchj2 = Double.valueOf(mg_info.get(57).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_CETRC_BSIX()) >= Double.valueOf(mg_info.get(59).getMIN()) && Double.valueOf(sub_info.get(0).getMD_CETRC_BSIX()) < Double.valueOf(mg_info.get(59).getMAX())) {
            grade_cqgchj2 = Double.valueOf(mg_info.get(59).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_CETRC_BSIX()) >= Double.valueOf(mg_info.get(60).getMIN()) && Double.valueOf(sub_info.get(0).getMD_CETRC_BSIX()) < Double.valueOf(mg_info.get(60).getMAX())) {
            grade_cqgchj2 = Double.valueOf(mg_info.get(60).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_CETRC_BSIX()) > Double.valueOf(mg_info.get(58).getMIN()) && Double.valueOf(sub_info.get(0).getMD_CETRC_BSIX()) < 999999) {
            grade_cqgchj2 = Double.valueOf(mg_info.get(58).getSCORE());
        } else {
            sub_info.get(0).setMD_CETRC_BSIX(String.valueOf(999999));
            grade_cqgchj2 = Double.valueOf(mg_info.get(58).getSCORE());
        }

        //6.3普通铁路运输系统轨道与任意一个测向的中心点的距离不小于1 km
        Double grade_cqgchj3 = null;
        if (Double.valueOf(sub_info.get(0).getMD_CR()) < Double.valueOf(mg_info.get(53).getMAX()) && sub_info.get(0).getMD_CR() != null) {
            grade_cqgchj3 = Double.valueOf(mg_info.get(53).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_CR()) >= Double.valueOf(mg_info.get(55).getMIN()) && Double.valueOf(sub_info.get(0).getMD_CR()) < Double.valueOf(mg_info.get(55).getMAX())) {
            grade_cqgchj3 = Double.valueOf(mg_info.get(55).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_CR()) >= Double.valueOf(mg_info.get(56).getMIN()) && Double.valueOf(sub_info.get(0).getMD_CR()) <= Double.valueOf(mg_info.get(56).getMAX())) {
            grade_cqgchj3 = Double.valueOf(mg_info.get(56).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_CR()) > Double.valueOf(mg_info.get(54).getMIN())) {
            grade_cqgchj3 = Double.valueOf(mg_info.get(54).getSCORE());
        } else {
            sub_info.get(0).setMD_CR(String.valueOf(999999));
            grade_cqgchj3 = Double.valueOf(mg_info.get(54).getSCORE());
        }

        //6.4 35kV以上、500kV以下高压交流输电线路与任一测量极的距离应不小于300m（1.0分）
        Double grade_cqgchj4 = null;
        if (Double.valueOf(sub_info.get(0).getMD_HVT_THRKTFK()) < Double.valueOf(mg_info.get(69).getMAX()) && sub_info.get(0).getMD_HVT_THRKTFK() != null) {
            grade_cqgchj4 = Double.valueOf(mg_info.get(69).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_HVT_THRKTFK()) >= Double.valueOf(mg_info.get(70).getMIN()) && Double.valueOf(sub_info.get(0).getMD_HVT_THRKTFK()) <= Double.valueOf(mg_info.get(70).getMAX())) {
            grade_cqgchj4 = Double.valueOf(mg_info.get(70).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_HVT_THRKTFK()) >= Double.valueOf(mg_info.get(72).getMIN()) && Double.valueOf(sub_info.get(0).getMD_HVT_THRKTFK()) <= Double.valueOf(mg_info.get(72).getMAX())) {
            grade_cqgchj4 = Double.valueOf(mg_info.get(72).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_HVT_THRKTFK()) > Double.valueOf(mg_info.get(71).getMIN())) {
            grade_cqgchj4 = Double.valueOf(mg_info.get(71).getSCORE());
        } else {
            sub_info.get(0).setMD_HVT_THRKTFK(String.valueOf(999999));
            grade_cqgchj4 = Double.valueOf(mg_info.get(71).getSCORE());
        }

        //6.5 500kV以上高压交流输电线路与任一测量极的距离应不小于1.5 km
        Double grade_cqgchj5 = null;
        if (Double.valueOf(sub_info.get(0).getMD_HVT_F()) < Double.valueOf(mg_info.get(65).getMAX()) && sub_info.get(0).getMD_HVT_F() != null) {
            grade_cqgchj5 = Double.valueOf(mg_info.get(65).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_HVT_F()) >= Double.valueOf(mg_info.get(67).getMIN()) && Double.valueOf(sub_info.get(0).getMD_HVT_F()) < Double.valueOf(mg_info.get(67).getMAX())) {
            grade_cqgchj5 = Double.valueOf(mg_info.get(67).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_HVT_F()) >= Double.valueOf(mg_info.get(68).getMIN()) && Double.valueOf(sub_info.get(0).getMD_HVT_F()) < Double.valueOf(mg_info.get(68).getMAX())) {
            grade_cqgchj5 = Double.valueOf(mg_info.get(68).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_HVT_F()) > Double.valueOf(mg_info.get(66).getMIN())) {
            grade_cqgchj5 = Double.valueOf(mg_info.get(66).getSCORE());
        } else {
            sub_info.get(0).setMD_HVT_F(String.valueOf(999999));
            grade_cqgchj5 = Double.valueOf(mg_info.get(66).getSCORE());
        }

        //6.6 30kW以下变压器或相当功率的用电器，接地线与任一测量极的距离应不小于50 m
        Double grade_cqgchj6 = null;
        if (Double.valueOf(sub_info.get(0).getMD_GWM()) < Double.valueOf(mg_info.get(78).getMAX()) && sub_info.get(0).getMD_GWM() != null) {
            grade_cqgchj6 = Double.valueOf(mg_info.get(78).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_GWM()) >= Double.valueOf(mg_info.get(75).getMIN()) && Double.valueOf(sub_info.get(0).getMD_GWM()) < Double.valueOf(mg_info.get(75).getMAX())) {
            grade_cqgchj6 = Double.valueOf(mg_info.get(75).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_GWM()) >= Double.valueOf(mg_info.get(76).getMIN()) && Double.valueOf(sub_info.get(0).getMD_GWM()) < Double.valueOf(mg_info.get(76).getMAX())) {
            grade_cqgchj6 = Double.valueOf(mg_info.get(76).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_GWM()) > Double.valueOf(mg_info.get(74).getMIN())) {
            grade_cqgchj6 = Double.valueOf(mg_info.get(74).getSCORE());
        } else {
            sub_info.get(0).setMD_GWM(String.valueOf(999999));
            grade_cqgchj6 = Double.valueOf(mg_info.get(74).getSCORE());
        }

        //6.7  30kW以上变压器或相当功率的用电器，接地线与任一测量极的距离应不小于100 m
        Double grade_cqgchj7 = null;
        if (Double.valueOf(sub_info.get(0).getMD_TGW_THRTK()) < Double.valueOf(mg_info.get(73).getMAX()) && sub_info.get(0).getMD_TGW_THRTK() != null) {
            grade_cqgchj7 = Double.valueOf(mg_info.get(73).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_TGW_THRTK()) >= Double.valueOf(mg_info.get(79).getMIN()) && Double.valueOf(sub_info.get(0).getMD_TGW_THRTK()) < Double.valueOf(mg_info.get(79).getMAX())) {
            grade_cqgchj7 = Double.valueOf(mg_info.get(79).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_TGW_THRTK()) >= Double.valueOf(mg_info.get(80).getMIN()) && Double.valueOf(sub_info.get(0).getMD_TGW_THRTK()) < Double.valueOf(mg_info.get(80).getMAX())) {
            grade_cqgchj7 = Double.valueOf(mg_info.get(80).getSCORE());
        } else if (Double.valueOf(sub_info.get(0).getMD_TGW_THRTK()) > Double.valueOf(mg_info.get(77).getMIN())) {
            grade_cqgchj7 = Double.valueOf(mg_info.get(77).getSCORE());
        } else {
            sub_info.get(0).setMD_TGW_THRTK(String.valueOf(999999));
            grade_cqgchj7 = Double.valueOf(mg_info.get(77).getSCORE());
        }
        grade_cqgchj = grade_cqgchj1 + grade_cqgchj2 + grade_cqgchj3 + grade_cqgchj4 + grade_cqgchj5 + grade_cqgchj6 + grade_cqgchj7;

        //仪器系统
        //7.1电极应采用铅板电极
        Double grade_qbdj = null;
        if (sub_info.get(0).getIS_ELF() == String.valueOf(mg_info.get(12).getTYPE())) {
            grade_qbdj = Double.valueOf(mg_info.get(12).getSCORE());
        } else if (sub_info.get(0).getIS_ELF() == String.valueOf(mg_info.get(13).getTYPE())) {
            grade_qbdj = Double.valueOf(mg_info.get(13).getSCORE());
        } else {
            sub_info.get(0).setIS_ELF(String.valueOf(999999));
            grade_qbdj = Double.valueOf(mg_info.get(13).getSCORE());
        }

        //8.1线电阻不应大于20Ω/km，拉断力不宜小于2000Ｎ
        Double grade_xdz_ldl = null;
        if (sub_info.get(0).getECR() == String.valueOf(mg_info.get(0).getTYPE())) {
            grade_xdz_ldl = Double.valueOf(mg_info.get(0).getSCORE());
        } else if (sub_info.get(0).getECR() == String.valueOf(mg_info.get(1).getTYPE())) {
            grade_xdz_ldl = Double.valueOf(mg_info.get(1).getSCORE());
        } else {
            sub_info.get(0).setECR(String.valueOf(999999));
            grade_xdz_ldl = Double.valueOf(mg_info.get(1).getSCORE());
        }

        //9.1宜有无间隙封闭式避雷器或有间隙放电式避雷器
        Double grade_bdq = null;
        if (sub_info.get(0).getIS_LPD() == String.valueOf(mg_info.get(14).getTYPE())) {
            grade_bdq = Double.valueOf(mg_info.get(14).getSCORE());
        } else if (sub_info.get(0).getIS_LPD() == String.valueOf(mg_info.get(15).getTYPE())) {
            grade_bdq = Double.valueOf(mg_info.get(15).getSCORE());
        } else {
            sub_info.get(0).setIS_LPD(String.valueOf(999999));
            grade_bdq = Double.valueOf(mg_info.get(15).getSCORE());
        }

        //辅助测向
        Double grade_fzcx = null;
        //10.1 应有潜水位观测
        Double grade_fzcx1 = null;
        if (sub_info.get(0).getIS_WLOB() == String.valueOf(mg_info.get(8).getTYPE())) {
            grade_fzcx1 = Double.valueOf(mg_info.get(8).getSCORE());
        } else if (sub_info.get(0).getIS_WLOB() == String.valueOf(mg_info.get(9).getTYPE())) {
            grade_fzcx1 = Double.valueOf(mg_info.get(9).getSCORE());
        } else {
            sub_info.get(0).setIS_WLOB(String.valueOf(999999));
            grade_fzcx1 = Double.valueOf(mg_info.get(9).getSCORE());
        }

        //10.2 应有降雨量观测
        Double grade_fzcx2 = null;
        if (sub_info.get(0).getIS_PREOB() == String.valueOf(mg_info.get(10).getTYPE())) {
            grade_fzcx2 = Double.valueOf(mg_info.get(10).getSCORE());
        } else if (sub_info.get(0).getIS_PREOB() == String.valueOf(mg_info.get(11).getTYPE())) {
            grade_fzcx2 = Double.valueOf(mg_info.get(11).getSCORE());
        } else {
            sub_info.get(0).setIS_PREOB(String.valueOf(999999));
            grade_fzcx2 = Double.valueOf(mg_info.get(11).getSCORE());
        }

        //10.3 应有温度观测
        Double grade_fzcx3 = null;
        if (sub_info.get(0).getIS_TEMOB() == String.valueOf(mg_info.get(6).getTYPE())) {
            grade_fzcx3 = Double.valueOf(mg_info.get(6).getSCORE());
        } else if (sub_info.get(0).getIS_TEMOB() == String.valueOf(mg_info.get(7).getTYPE())) {
            grade_fzcx3 = Double.valueOf(mg_info.get(7).getSCORE());
        } else {
            sub_info.get(0).setIS_TEMOB(String.valueOf(999999));
            grade_fzcx3 = Double.valueOf(mg_info.get(7).getSCORE());
        }

        grade_fzcx = grade_fzcx1 + grade_fzcx2 + grade_fzcx3;


        //基础资料
        Double grade_jczl = null;

        //11.1 应有电测深曲线或高密度剖面资料
        Double grade_jczl1 = null;
        if (sub_info.get(0).getIS_ESC_HDPD() == String.valueOf(mg_info.get(2).getTYPE())) {
            grade_jczl1 = Double.valueOf(mg_info.get(2).getSCORE());
        } else if (sub_info.get(0).getIS_ESC_HDPD() == String.valueOf(mg_info.get(3).getTYPE())) {
            grade_jczl1 = Double.valueOf(mg_info.get(3).getSCORE());
        } else {
            sub_info.get(0).setIS_ESC_HDPD(String.valueOf(999999));
            grade_jczl1 = Double.valueOf(mg_info.get(3).getSCORE());
        }

        //11.2 应有测区地层剖面或柱状资料
        Double grade_jczl2 = null;
        if (sub_info.get(0).getIS_FP_CD() == String.valueOf(mg_info.get(4).getTYPE())) {
            grade_jczl2 = Double.valueOf(mg_info.get(4).getSCORE());
        } else if (sub_info.get(0).getIS_FP_CD() == String.valueOf(mg_info.get(5).getTYPE())) {
            grade_jczl2 = Double.valueOf(mg_info.get(5).getSCORE());
        } else {
            sub_info.get(0).setIS_FP_CD(String.valueOf(999999));
            grade_jczl2 = Double.valueOf(mg_info.get(5).getSCORE());
        }

        grade_jczl = grade_jczl1 + grade_jczl1 + grade_jczl2;
        Double grade_all6 = null;
        grade_all6 = grade_dzdm + grade_swdz + grade_yx + grade_dxjg + grade_cddc + grade_cqgchj;
        Double grade_all11 = null;
        grade_all11 = grade_qbdj + grade_xdz_ldl + grade_bdq + grade_fzcx + grade_jczl;
        Double grade_all = null;
        grade_all = grade_all6 + grade_all11;

        List<Object> r = new ArrayList<>();
        r.set(0, sub_info);
        r.set(1, grade_dzdm1);
        r.set(2, grade_dzdm2);
        r.set(3, grade_dzdm3);
        r.set(4, grade_swdz1);
        r.set(5, grade_swdz2);
        r.set(6, grade_yx1);
        r.set(7, grade_yx2);
        r.set(8, grade_dxjg1);
        r.set(9, grade_dxjg2);
        r.set(10, grade_cddc1);
        r.set(11, grade_cddc2);
        r.set(12, grade_cqgchj1);
        r.set(13, grade_cqgchj2);
        r.set(14, grade_cqgchj3);
        r.set(15, grade_cqgchj4);
        r.set(16, grade_cqgchj5);
        r.set(17, grade_cqgchj6);
        r.set(18, grade_cqgchj7);
        r.set(19, grade_qbdj);
        r.set(20, grade_xdz_ldl);
        r.set(21, grade_bdq);
        r.set(22, grade_fzcx1);
        r.set(23, grade_fzcx2);
        r.set(24, grade_fzcx3);
        r.set(25, grade_jczl1);
        r.set(26, grade_jczl2);
        r.set(27, grade_all6);
        r.set(28, grade_all11);
        r.set(28, grade_all);
        return r;
    }



    //地电阻查询(改)-跟踪应用评估

    public List<Object> DDZ_EarthResistivity(String station_id, String item_id, String point_id) {
        String Ear_item_id = item_id.substring(0,3);
        String Ear_point_id = point_id;

        List<BI_SUB_EARTHRESISTIVITY> AllEarthData = bi_sub_earthresistivity_mapper.getAllEarthData(station_id,Ear_item_id,Ear_point_id);

        Integer mouthTime = bi_monthly_report_mapper.getMouthTime(station_id,item_id,point_id);

        List<Integer> Alldate = bi_monthly_report_mapper.getMouthGrade(mouthTime);

        Integer grade = bi_monthly_report_mapper.getgrade(station_id,Ear_item_id,Ear_point_id,1,mouthTime);

        //Integer alldate = bi_monthly_report_mapper.getgrade(Ear_point_id);




        //权重提取
        List<MG_SCOREMANAGER_DDZ> mouthgrade = mg_scoremanager_ddz_mapper.getmouthgrade();

        Integer mgrade = grade;

        List<BI_MONTHLY_REPORT> MarkArray = bi_monthly_report_mapper.getMarkArray();

        //获取最大值下标
        List<BI_MONTHLY_REPORT> a[] = MarkArray.toArray(new List[0]);
        int key = IntStream.range(0, a.length).reduce((i, j) -> Double.valueOf(String.valueOf(a[i])) >Double.valueOf(String.valueOf(a[j])) ? j : i).getAsInt();
        String HighestMark = MarkArray.get(key).getSCORE();

        Double usegrade;
        if(mgrade == null || mgrade == 999999){
            mgrade = 999999;
            mouthgrade.get(0).setSCORE(9999.00) ;
            usegrade = 0.00;
        }else{
            usegrade = Double.valueOf(mouthgrade.get(0).getSCORE()) * (mgrade / Double.valueOf(HighestMark));
        }
        usegrade = Double.valueOf(Math.round(usegrade * 100) / 100);
        //usegrade = Math.round(usegrade * 100) / 100;


        //二、系统运行状态2.1.1 供电线漏电电流与供电电流的比值不应大于0.1％，供电线漏电电位差绝对值与人工电位差的比值不应大于0.5％
        Double IS_LCPS = Double.valueOf(AllEarthData.get(0).getIS_LCPS());
        List<MG_SCOREMANAGER_DDZ> ElectricityRatio = mg_scoremanager_ddz_mapper.getElectricityRatio();
        Double ElectricityRatioGrade = null;
        for(int i = 0;i<=1;i++){
            if(Double.valueOf(IS_LCPS) == 999999){
                ElectricityRatioGrade = 0.00;
            }else if(Double.valueOf(IS_LCPS) == Double.valueOf(ElectricityRatio.get(i).getTYPE())){
                ElectricityRatioGrade = Double.valueOf(ElectricityRatio.get(i).getSCORE());
            }else if(Double.valueOf(IS_LCPS) == null){
                IS_LCPS = 999999.00;
                ElectricityRatioGrade = 0.00;
            }
        }

        //二、系统运行状态2.1.2 测量线对地绝缘电阻不应小于5 MΩ
        Double IS_MLGI_SFM = Double.valueOf(AllEarthData.get(0).getIS_MLGI_SFM());
        List<MG_SCOREMANAGER_DDZ> ResistanceCotton = mg_scoremanager_ddz_mapper.getResistanceCotton();
        Double ResistanceCottonGrade = null;
        for(int i = 0;i<=1;i++){
            if(Double.valueOf(IS_MLGI_SFM) == 999999){
                ResistanceCottonGrade = 0.00;
            }else if(Double.valueOf(IS_MLGI_SFM) == Double.valueOf(ResistanceCotton.get(i).getTYPE())){
                ResistanceCottonGrade = Double.valueOf(ResistanceCotton.get(i).getSCORE());
            }else if(Double.valueOf(IS_MLGI_SFM) == null){
                IS_MLGI_SFM = 999999.00;
                ResistanceCottonGrade = 0.00;
            }
        }

        //二、系统运行状态2.2.1 供电电极单电极接地电阻不应大于30Ω
        Double IS_SEGR_THRO = Double.valueOf(AllEarthData.get(0).getIS_SEGR_THRO());
        List<MG_SCOREMANAGER_DDZ> TouchResistance = mg_scoremanager_ddz_mapper.getTouchResistance();
        Double TouchResistanceGrade = null;
        for(int i = 0;i<=1;i++){
            if(Double.valueOf(IS_SEGR_THRO) == 999999){
                TouchResistanceGrade = 0.00;
            }else if(Double.valueOf(IS_SEGR_THRO) == Double.valueOf(TouchResistance.get(i).getTYPE())){
                TouchResistanceGrade = Double.valueOf(TouchResistance.get(i).getSCORE());
            }else if(Double.valueOf(IS_SEGR_THRO) == null){
                IS_SEGR_THRO = 999999.00;
                TouchResistanceGrade = 0.00;
            }
        }

        //二、系统运行状态2.2.2 测量电极单电极接地电阻不应大于100Ω
        Double IS_MSE_HRUO = Double.valueOf(AllEarthData.get(0).getIS_MSE_HRUO());
        List<MG_SCOREMANAGER_DDZ> TouchResistanceOne = mg_scoremanager_ddz_mapper.getTouchResistanceOne();
        Double TouchResistanceOneGrade = null;
        for(int i = 0;i<=1;i++){
            if(Double.valueOf(IS_MSE_HRUO) == 999999){
                TouchResistanceOneGrade = 0.00;
            }else if(Double.valueOf(IS_MSE_HRUO) == Double.valueOf(TouchResistanceOne.get(i).getTYPE())){
                TouchResistanceOneGrade = Double.valueOf(TouchResistanceOne.get(i).getSCORE());
            }else if(Double.valueOf(IS_MSE_HRUO) == null){
                IS_MSE_HRUO = 999999.00;
                TouchResistanceOneGrade = 0.00;
            }
        }

        //二、系统运行状态2.3.1 输出电流：0.5 A～2.5 A
        Double IS_OUTCU_BET_A = Double.valueOf(AllEarthData.get(0).getIS_OUTCU_BET_A());
        List<MG_SCOREMANAGER_DDZ> OutElectricity = mg_scoremanager_ddz_mapper.getOutElectricity();
        Double OutElectricityGrade = null;
        for(int i = 0;i<=1;i++){
            if(Double.valueOf(IS_OUTCU_BET_A) == 999999){
                OutElectricityGrade = 0.00;
            }else if(Double.valueOf(IS_OUTCU_BET_A) == Double.valueOf(OutElectricity.get(i).getTYPE())){
                OutElectricityGrade = Double.valueOf(OutElectricity.get(i).getSCORE());
            }else if(Double.valueOf(IS_OUTCU_BET_A) == null){
                IS_OUTCU_BET_A = 999999.00;
                OutElectricityGrade = 0.00;
            }
        }

        //二、系统运行状态2.3.2 纹波因数：小于0.5％
        Double IS_RIFA_S = Double.valueOf(AllEarthData.get(0).getIS_RIFA_S());
        List<MG_SCOREMANAGER_DDZ> WBYS = mg_scoremanager_ddz_mapper.getWBYS();
        Double WBYSGrade = null;
        for(int i = 0;i<=1;i++){
            if(Double.valueOf(IS_RIFA_S) == 999999){
                WBYSGrade = 0.00;
            }else if(Double.valueOf(IS_RIFA_S) == Double.valueOf(WBYS.get(i).getTYPE())){
                WBYSGrade = Double.valueOf(WBYS.get(i).getSCORE());
            }else if(Double.valueOf(IS_RIFA_S) == null){
                IS_RIFA_S = 999999.00;
                WBYSGrade = 0.00;
            }
        }

        //二、系统运行状态2.3.2 电流稳定度：优于0.5％
        Double IS_CSB_ = Double.valueOf(AllEarthData.get(0).getIS_CSB_());
        List<MG_SCOREMANAGER_DDZ> DLWDD = mg_scoremanager_ddz_mapper.getDLWDD();
        Double DLWDDGrade = null;
        for(int i = 0;i<=1;i++){
            if(Double.valueOf(IS_CSB_) == 999999){
                DLWDDGrade = 0.00;
            }else if(Double.valueOf(IS_CSB_) == Double.valueOf(DLWDD.get(i).getTYPE())){
                DLWDDGrade = Double.valueOf(DLWDD.get(i).getSCORE());
            }else if(Double.valueOf(IS_CSB_) == null){
                IS_CSB_ = 999999.00;
                DLWDDGrade = 0.00;
            }
        }

        //二、系统运行状态2.4.1-2.4.9 测量自然电位差时分辨率不低于0.01 mV；
        Double IS_OBINSRE = Double.valueOf(AllEarthData.get(0).getIS_OBINSRE());
        List<MG_SCOREMANAGER_DDZ> GCYQ = mg_scoremanager_ddz_mapper.getGCYQ();
        Double GCYQGrade = null;
        for(int i = 0;i<=1;i++){
            if(Double.valueOf(IS_OBINSRE) == 999999){
                GCYQGrade = 0.00;
            }else if(Double.valueOf(IS_OBINSRE) == Double.valueOf(GCYQ.get(i).getTYPE())){
                GCYQGrade = Double.valueOf(GCYQ.get(i).getSCORE());
            }else if(Double.valueOf(IS_OBINSRE) == null){
                IS_OBINSRE = 999999.00;
                GCYQGrade = 0.00;
            }
        }

        //三、观测干扰情况3.1.1 基建施工、金属管网设施类干扰源
        List<MG_SCOREMANAGER_DDZ> JJZF = mg_scoremanager_ddz_mapper.getJJZF();
        Double COIN_INSOU_NUM = Double.valueOf(AllEarthData.get(0).getCOIN_INSOU_NUM());
        List<MG_SCOREMANAGER_DDZ> SSLGRY = mg_scoremanager_ddz_mapper.getSSLGRY();
        Double SSLGRYGrade = null;
        if(COIN_INSOU_NUM == null || COIN_INSOU_NUM == 999999){
            COIN_INSOU_NUM = 999999.00;
            SSLGRYGrade = 0.00;
        }else if(COIN_INSOU_NUM <= 10 && COIN_INSOU_NUM != null){
            SSLGRYGrade = Double.valueOf(JJZF.get(0).getSCORE()) + Double.valueOf(COIN_INSOU_NUM) * Double.valueOf(SSLGRY.get(0).getSCORE());
        }else if(COIN_INSOU_NUM > 10 && COIN_INSOU_NUM < 999999){
            SSLGRYGrade = 0.00;
        }else{
            SSLGRYGrade = 0.00;
            COIN_INSOU_NUM = 999999.00;
        }

        //三、观测干扰情况3.2.1 自然环境类干扰源
        List<MG_SCOREMANAGER_DDZ> ZRZF = mg_scoremanager_ddz_mapper.getZRZF();
        Double INSOEN_NUM = Double.valueOf(AllEarthData.get(0).getINSOEN_NUM());
        List<MG_SCOREMANAGER_DDZ> ZRLGRY = mg_scoremanager_ddz_mapper.getZRLGRY();
        Double ZRLGRYGrade = null;
        if(INSOEN_NUM == 999999){
            ZRLGRYGrade = 0.00;
        }else if(INSOEN_NUM <= 5 && INSOEN_NUM != null){
            ZRLGRYGrade = Double.valueOf(ZRZF.get(0).getSCORE()) + Double.valueOf(INSOEN_NUM) * Double.valueOf(ZRLGRY.get(0).getSCORE());
        }else if(INSOEN_NUM >5){
            ZRLGRYGrade = 0.00;
        }else{
            INSOEN_NUM = 999999.00;
            ZRLGRYGrade = 0.00;
        }

        //四、震例情况
        String ExempleEarthQuake = JC_EQ_situation(station_id,item_id,point_id);
        String[] separate = ExempleEarthQuake .split("||");
        String EarthQuakeConnect = separate[0];
        String EarthQuakevalue = separate[1];
        int EleQuaGrade = 0;
        if(EarthQuakeConnect == "不符合前述条件的其他情况"){
            EleQuaGrade = 0;
        }else if(EarthQuakeConnect == "近5年内震例预报效能"){
            if(Integer.valueOf(EarthQuakevalue) >= 3){
                EleQuaGrade = 25;
            }else if(Integer.valueOf(EarthQuakevalue) == 2){
                EleQuaGrade = 20;
            }else if(Integer.valueOf(EarthQuakevalue) == 1){
                EleQuaGrade = 15;
            }else{
                EleQuaGrade = 999999;
            }
        }else if(EarthQuakeConnect == "近5年内无震例,但观测以来存在震例"){
            if(Integer.valueOf(EarthQuakevalue) >= 3){
                EleQuaGrade = 15;
            }else if(Integer.valueOf(EarthQuakevalue) == 2){
                EleQuaGrade = 12;
            }else if(Integer.valueOf(EarthQuakevalue) ==1){
                EleQuaGrade = 10;
            }else{
                EleQuaGrade = 999999;
            }
        }else if(EarthQuakeConnect == "台项周围200km内有5级以上地震"){
            EleQuaGrade = 5;
        }

        Double sum ;
        sum = ElectricityRatioGrade + usegrade + ResistanceCottonGrade + TouchResistanceGrade + TouchResistanceOneGrade + OutElectricityGrade + WBYSGrade + DLWDDGrade + GCYQGrade + SSLGRYGrade + ZRLGRYGrade + EleQuaGrade;
        //返回数组
        List<Object> arr = new ArrayList<>();
        if (mgrade == ' ') {
            //arr[0] = 0; //月报成绩
            arr.set(0,0);

        } else {
            //月报成绩
            arr.set(0,mgrade);

        }
        arr.set(1,usegrade);

        arr.set(2, ElectricityRatioGrade);//二、系统运行状态2.1.1 供电线漏电电流与供电电流的比值不应大于0.1％，供电线漏电电位差绝对值与人工电位差的比值不应大于0.5％
        arr.set(3, ResistanceCottonGrade);//二、系统运行状态2.1.2 测量线对地绝缘电阻不应小于5 MΩ
        arr.set(4, TouchResistanceGrade);//二、系统运行状态2.2.1 供电电极单电极接地电阻不应大于30Ω
        arr.set(5, TouchResistanceOneGrade); //二、系统运行状态2.2.2 测量电极单电极接地电阻不应大于100Ω
        arr.set(6, OutElectricityGrade);//二、系统运行状态2.3.1 输出电流：0.5 A～2.5 A
        arr.set(7, WBYSGrade); //二、系统运行状态2.3.2 纹波因数：小于0.5％
        arr.set(8, DLWDDGrade);//二、系统运行状态2.3.2 电流稳定度：优于0.5％
        arr.set(9, GCYQGrade);//二、系统运行状态2.4.1-2.4.9 测量自然电位差时分辨率不低于0.01 mV；
        arr.set(10, SSLGRYGrade);//三、观测干扰情况3.1.1 基建施工、金属管网设施类干扰源
        arr.set(11, ZRLGRYGrade);//三、观测干扰情况3.2.1 自然环境类干扰源
        arr.set(12, ElectricityRatioGrade); //四、震例情况内容
        arr.set(13, ElectricityRatioGrade);//四、震例情况值
        arr.set(14, COIN_INSOU_NUM);
        arr.set(15, INSOEN_NUM);
        arr.set(16, IS_LCPS);
        arr.set(17, IS_MLGI_SFM);
        arr.set(18, IS_SEGR_THRO);
        arr.set(19, IS_MSE_HRUO);
        arr.set(20, IS_OUTCU_BET_A);
        arr.set(21, IS_RIFA_S);
        arr.set(22, IS_CSB_);
        arr.set(23, IS_OBINSRE);
        arr.set(24, sum);
        arr.set(25, EleQuaGrade);
        return  arr;
    }


    public  String JC_EQ_situation( String station_Name,String station_id,String item_id){

        QZ_DICT_STATIONS stationPositionSql = qz_dict_stations_mapper.getstationPositionSql(station_id);
        //得出当前台站坐标以确定距离
        //圆心位置经纬度
        Double latitude1 = stationPositionSql.getLATITUDE();
        Double longitude1 = stationPositionSql.getLONGITUDE();

        //当前时间
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat nowtime = new SimpleDateFormat("yyyyMMddhhmmss");
        String myDateTime = nowtime.format(cal.getTime());

        //此处查询历史地震 以此点为圆心200公里范围内五年内的历史地震
        List<HI_HISTORYEQ> historyData = hi_historyeq_mapper.gethistoryData();
        List<BI_ITEMKEY> key = bi_itemkey_mapper.getkey();
        Double keyWord = Double.valueOf(key.get(0).getKEYWORD());
        station_Name = station_Name.replace("地震台"," "); //模糊掉地震台三个字

        List<EI_PRECURSORYANOMALY> anormal = ei_precursoryanomaly_mapper.getanormal();

        int fiveYearIncludeNum = 0;//近5年内有3次以上（含3次）震例
        int fiveObserveNum = 0 ;//观测以来存在震例的次数
        int isHaveFive = 0;//小于二百公里并且震级大于五级

        List<Date> eqTime = new ArrayList<>();
        SimpleDateFormat tempFmt = new SimpleDateFormat("yyyyMMddhhmmss");
        for(int j = 0; j < anormal.size(); j++ ){
            //近5年内有3次以上（含3次）震例
            // 这里的逻辑你写一下 我不确定 getEQID 拿到的值是无法
            // 构建一个日期对象
            try
            {
                // 到時候根據EQID在数据库中的格式 ，修改上面的 tempFmt
                Date tempDate = tempFmt.parse( anormal.get(j).getEQID() ) ; //  anormal.get(j).getEQID()
                eqTime.set(j, tempDate ) ;
            }catch (ParseException e ){
                e.printStackTrace();
            }
        }
        Date sel_date = new Date("yyyyMMddhhmmss");
        //DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        for(int i =0;i <eqTime.size();i++){
            //近5年内震例预报效能（20）分；

            if(((sel_date.getTime() - eqTime.get(i).getTime()) / (1000*60*60*24)) <= 5){
                //五年内存在震例
                fiveYearIncludeNum++;
            }else{
                //否则就是五年内不存在震例
                fiveYearIncludeNum = 0;
                fiveObserveNum = eqTime.size();//观测以来存在震例的次数
            }
        }

        //给出相应情况的不同次数
        for (int k = 0;k< historyData.size();k++){
            Double latitude2 = Double.valueOf(historyData.get(k).getLATI());//当前地震经度
            Double longitude2 = Double.valueOf(historyData.get(k).getLONGI());//当前数据的纬度
            Double mag = Double.valueOf(historyData.get(k).getMAG());//当前数据地震震级
            Double dateTime = Double.valueOf(historyData.get(k).getEQDATE());//数据中的时间
            if(Double.valueOf(getDistance(latitude1,longitude1,latitude2,longitude2)) <= 200 && mag >5){
                //小于二百公里并且震级大于五级
                isHaveFive++;
            }
        }
        //给分处理
        if (fiveYearIncludeNum != 0) {
            //近5年内有3次以上（含3次）震例
            if (fiveYearIncludeNum == 1){
                return "近5年内震例预报效能||" + fiveYearIncludeNum;
            }
            if (fiveYearIncludeNum == 2) {
                return "近5年内震例预报效能||" + fiveYearIncludeNum;
            }
            if (fiveYearIncludeNum >= 3) {
                return "近5年内震例预报效能||" + fiveYearIncludeNum;
            }
        } else if (fiveObserveNum != 0) {
            //（2）近5年内无震例，但观测以来存在震例
            if (fiveObserveNum == 1) {
                return "近5年内无震例,但观测以来存在震例||" + fiveObserveNum;
            }
            if (fiveObserveNum == 2) {
                return "近5年内无震例,但观测以来存在震例||" + fiveObserveNum;
            }
            if (fiveObserveNum >= 3) {
                return "近5年内无震例,但观测以来存在震例||" + fiveObserveNum;
            }
        } else if (isHaveFive != 0) {
            return "台项周围200km内有5级以上地震||" + isHaveFive; //3

        } else {
            // "不符合前述条件的其他情况||0  return "不符合前述条件的其他情况" . "||" . '0'; //10
            return "不符合前述条件的其他情况||0" ; //10

        }
        return null;
    }

    public static double getDistance(Double lat1,Double lng1,Double lat2,Double lng2) {
        // 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
        double radiansAX = Math.toRadians(lng1); // A经弧度
        double radiansAY = Math.toRadians(lat1); // A纬弧度
        double radiansBX = Math.toRadians(lng2); // B经弧度
        double radiansBY = Math.toRadians(lat2); // B纬弧度

        // 公式中“cosβ1cosβ2cos（α1-α2）+sinβ1sinβ2”的部分，得到∠AOB的cos值
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                + Math.sin(radiansAY) * Math.sin(radiansBY);
//        System.out.println("cos = " + cos); // 值域[-1,1]
        double acos = Math.acos(cos); // 反余弦值
//        System.out.println("acos = " + acos); // 值域[0,π]
//        System.out.println("∠AOB = " + Math.toDegrees(acos)); // 球心角 值域[0,180]
        return EARTH_RADIUS * acos; // 最终结果
    }



}











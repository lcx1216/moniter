package com.gis.monitor.mapper.basicinfo;



import com.gis.monitor.pojo.basicinfo.BI_MONTHLY_REPORT;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BI_MONTHLY_REPORT_MAPPER {
    List<BI_MONTHLY_REPORT> getRatingTimeList();

    List<Integer> getMouthGrade(Integer mouthTime);

    List<BI_MONTHLY_REPORT> getMIN();

    List<BI_MONTHLY_REPORT> getMAX();

    List<BI_MONTHLY_REPORT> getTYPE();

    List<BI_MONTHLY_REPORT> getMarkArray();


     Integer getMouthTime(String station_id,String item_id,String point_id);

    Integer getgrade(String station_id,String item_id,String point_id,Integer state,Integer mouthTime);
}

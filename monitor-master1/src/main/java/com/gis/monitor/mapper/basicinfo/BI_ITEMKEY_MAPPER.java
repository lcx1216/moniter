package com.gis.monitor.mapper.basicinfo;



import com.gis.monitor.pojo.basicinfo.BI_ITEMKEY;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BI_ITEMKEY_MAPPER {
    List<BI_ITEMKEY> getkey();

}

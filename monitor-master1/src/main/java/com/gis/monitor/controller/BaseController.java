package com.gis.monitor.controller;

// 获取基础相关信息

import com.gis.monitor.pojo.qzdata.QZ_DICT_STATIONS;
import com.gis.monitor.service.BaseService;
import com.gis.monitor.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/base")
@CrossOrigin
public class BaseController {
    @Autowired
    BaseService baseService;

    @GetMapping("/getStations")
    @ResponseBody
    public JsonResult getStations(){
        List<QZ_DICT_STATIONS> map = baseService.getStations();
        return JsonResult.success(map);
    }
}

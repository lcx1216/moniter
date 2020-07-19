package com.gis.monitor;

import com.gis.monitor.mapper.basicinfo.BI_USERS_MAPPER;
import com.gis.monitor.pojo.basicinfo.BI_USERS;
import com.gis.monitor.service.DDZ_PF_Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class MonitorApplicationTests {

    @Autowired
    private BI_USERS_MAPPER bi_users_mapper;

    @Autowired
    private DDZ_PF_Service ddz_pf_service;


    @Test
    void contextLoads() throws SQLException {
        BI_USERS bi_users = bi_users_mapper.login("DIDIANZU", "didianzu");
        System.out.println(bi_users);
    }

    @Test
    void MG_PF_DDZ() {
        HashMap<String, Object> input = new HashMap<>();
        Map ouput = ddz_pf_service.MG_PF_DDZ(input);
        System.out.println(ouput);
    }

}

package com.gis.monitor.service;


import com.gis.monitor.error.BaseException;
import com.gis.monitor.error.UserException;
import com.gis.monitor.mapper.basicinfo.BI_USERS_MAPPER;
import com.gis.monitor.pojo.basicinfo.BI_USERS;
import com.gis.monitor.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    @Autowired
    private BI_USERS_MAPPER bi_users_mapper;

    public BI_USERS getUserByUserName(String USERNAME){
        return bi_users_mapper.findUserByUserName(USERNAME);
    }

    public String Login(String USERNAME, String PASSWORD) throws BaseException {
        Assert.notNull(USERNAME, "用户名不能为空");
        Assert.notNull(PASSWORD, "密码不能为空");
        if(StringUtils.isBlank(USERNAME)){
            throw new UserException(UserException.ERROR_NOT_HAS_USERNAME);
        }
        if(StringUtils.isBlank(PASSWORD)){
            throw new UserException(UserException.ERROR_NOT_HAS_PASSWORD);
        }
        BI_USERS bi_users = bi_users_mapper.findUserByUserName(USERNAME);
        if(bi_users == null){
            throw new UserException(UserException.ERROR_USER_NOT_EXIT);
        }
        if(bi_users.getPASSWORD().equals(PASSWORD)) {
            return JwtUtil.sign(USERNAME, PASSWORD);
        }else {
            return null;
        }
    }
}

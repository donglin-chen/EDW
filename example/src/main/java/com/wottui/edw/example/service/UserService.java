package com.wottui.edw.example.service;

import com.wottui.edw.example.dao.UserMapper;
import com.wottui.edw.example.dao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * <b>创建日期：</b> 2019/6/6
 * </p>
 *
 * @author chendonglin
 * @since 1.0.0-SNAPSHOT
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void test(){
        User user = new User();
        user.setId(1L);
        user.setUsername("FSFSF");
        userMapper.updateByPrimaryKeySelective(user);
    }


}

package com.wottui.edw.example.controller;

import com.wottui.edw.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * <b>创建日期：</b> 2019/6/6
 * </p>
 *
 * @author chendonglin
 * @since 1.0.0-SNAPSHOT
 */
@RestController
public class SimpleController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public Object test() {
        userService.test();
        return "success";
    }

}

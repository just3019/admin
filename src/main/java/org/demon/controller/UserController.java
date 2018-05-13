/**
 * 项目名：admin
 * 包名：org.demon.controller
 * 文件名：UserController
 * 日期：2018/5/11-下午11:03
 * Copyright (c) 2018
 */
package org.demon.controller;

import org.demon.bean.UserQuery;
import org.demon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：UserController
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/11 下午11:03
 * 修改人：
 * 修改时间：2018/5/11 下午11:03
 * 修改备注：
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public Object list(@RequestBody UserQuery query) { return userService.selectUsers(query);
    }
}

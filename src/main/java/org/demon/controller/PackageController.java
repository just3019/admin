/**
 * 项目名：admin
 * 包名：org.demon.controller
 * 文件名：PackageController
 * 日期：2018/5/12-下午4:32
 * Copyright (c) 2018
 */
package org.demon.controller;

import org.demon.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：PackageController
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/12 下午4:32
 * 修改人：
 * 修改时间：2018/5/12 下午4:32
 * 修改备注：
 */
@RestController
public class PackageController {

    @Autowired
    private PackageService packageService;


}

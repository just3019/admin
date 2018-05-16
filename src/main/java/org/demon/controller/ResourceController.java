/**
 * 项目名：admin
 * 包名：org.demon.controller
 * 文件名：ResourceController
 * 日期：2018/5/13-上午11:47
 * Copyright (c) 2018
 */
package org.demon.controller;

import org.demon.bean.ResourceQuery;
import org.demon.code.ResultCodes;
import org.demon.exception.CodeException;
import org.demon.service.ResourceService;
import org.demon.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：ResourceController
 * 类描述：  
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/13 上午11:47  
 * 修改人：  
 * 修改时间：2018/5/13 上午11:47  
 * 修改备注：      
 */
@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;


    @RequestMapping(value = "resources", method = RequestMethod.POST)
    public Object list(@RequestBody ResourceQuery query){
        return resourceService.selectResources(query);
    }

    @RequestMapping(value = "resource/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable Integer id){
        if (NumberUtil.isInValidId(id)){
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS, "id");
        }
        return resourceService.selectInfos(id);
    }

}

/**
 * 项目名：admin
 * 包名：org.demon.controller
 * 文件名：ModuleController
 * 日期：2018/5/12-下午4:43
 * Copyright (c) 2018
 */
package org.demon.controller;

import org.demon.bean.ModuleQuery;
import org.demon.code.ResultCodes;
import org.demon.exception.CodeException;
import org.demon.service.ModuleService;
import org.demon.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：ModuleController
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/12 下午4:43
 * 修改人：
 * 修改时间：2018/5/12 下午4:43
 * 修改备注：
 */
@RestController
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @RequestMapping(value = "/module/{packageId}", method = RequestMethod.GET)
    public Object get(@PathVariable Integer packageId) {
        if (NumberUtil.isInValidId(packageId)) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS, "packageId");
        }
        ModuleQuery query = new ModuleQuery();
        query.packageId = packageId;
        return moduleService.selectModules(query);
    }
}

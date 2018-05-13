/**
 * 项目名：admin
 * 包名：org.demon.controller
 * 文件名：CatalogController
 * 日期：2018/5/13-上午11:20
 * Copyright (c) 2018
 */
package org.demon.controller;

import org.demon.bean.CatalogQuery;
import org.demon.code.ResultCodes;
import org.demon.exception.CodeException;
import org.demon.service.CatalogService;
import org.demon.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：CatalogController
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/13 上午11:20
 * 修改人：
 * 修改时间：2018/5/13 上午11:20
 * 修改备注：
 */
@RestController
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @RequestMapping(value = "/catalog/{packageId}", method = RequestMethod.GET)
    public Object get(@PathVariable Integer packageId) {
        if (NumberUtil.isInValidId(packageId)) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS, "packageId");
        }
        CatalogQuery query = new CatalogQuery();
        query.packageId = packageId;
        return catalogService.selectCatalogs(query);
    }
}

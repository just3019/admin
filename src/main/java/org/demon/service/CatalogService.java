/**
 * 项目名：admin
 * 包名：org.demon.service
 * 文件名：CatalogService
 * 日期：2018/5/13-上午11:20
 * Copyright (c) 2018
 */
package org.demon.service;

import org.demon.bean.CatalogBean;
import org.demon.bean.CatalogQuery;
import org.demon.bean.PageData;
import org.demon.mapper.CatalogMapper;
import org.demon.pojo.Catalog;
import org.demon.pojo.CatalogExample;
import org.demon.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 类名称：CatalogService
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/13 上午11:20
 * 修改人：
 * 修改时间：2018/5/13 上午11:20
 * 修改备注：
 */
@Service
public class CatalogService {

    @Autowired
    private CatalogMapper catalogMapper;

    public PageData<CatalogBean> selectCatalogs(CatalogQuery query) {
        CatalogExample example = new CatalogExample();
        CatalogExample.Criteria criteria = example.createCriteria();
        if (NumberUtil.isValidId(query.packageId)) {
            criteria.andPackageIdEqualTo(query.packageId);
        }
        PageData<CatalogBean> pageData = new PageData<>();
        pageData.count = catalogMapper.countByExample(example);
        example.setLimit(query.getLimit());
        example.setOffset(query.getOffset());
        List<Catalog> list = catalogMapper.selectByExample(example);
        pageData.list = Optional.ofNullable(list)
                .orElseGet(ArrayList::new)
                .stream()
                .map(this::convertCatalogBean)
                .collect(Collectors.toList());
        return pageData;
    }

    private CatalogBean convertCatalogBean(Catalog catalog) {
        CatalogBean bean = new CatalogBean();
        bean.id = catalog.getId();
        bean.name = catalog.getName();
        bean.packageId = catalog.getPackageId();
        bean.sort = catalog.getSort();
        bean.url = catalog.getUrl();
        return bean;
    }
}

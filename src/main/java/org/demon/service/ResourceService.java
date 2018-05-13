/**
 * 项目名：admin
 * 包名：org.demon.service
 * 文件名：ResourceService
 * 日期：2018/5/13-上午11:47
 * Copyright (c) 2018
 */
package org.demon.service;

import org.demon.bean.PageData;
import org.demon.bean.ResourceBean;
import org.demon.bean.ResourceQuery;
import org.demon.mapper.ResourceMapper;
import org.demon.pojo.Resource;
import org.demon.pojo.ResourceExample;
import org.demon.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 类名称：ResourceService
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/13 上午11:47
 * 修改人：
 * 修改时间：2018/5/13 上午11:47
 * 修改备注：
 */
@Service
public class ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;


    public PageData<ResourceBean> selectResources(ResourceQuery query) {
        ResourceExample example = new ResourceExample();
        ResourceExample.Criteria criteria = example.createCriteria();
        if (NumberUtil.isValidId(query.catalogId)) {
            criteria.andCatalogIdEqualTo(query.catalogId);
        }
        PageData<ResourceBean> pageData = new PageData<>();
        pageData.count = resourceMapper.countByExample(example);
        example.setLimit(query.getLimit());
        example.setOffset(query.getOffset());
        example.setOrderByClause(" create_time desc ");
        List<Resource> list = resourceMapper.selectByExample(example);
        pageData.list = Optional.ofNullable(list)
                .orElseGet(ArrayList::new)
                .stream()
                .map(this::convertResourceBean)
                .collect(Collectors.toList());
        return pageData;
    }

    private ResourceBean convertResourceBean(Resource resource) {
        ResourceBean bean = new ResourceBean();
        bean.id = resource.getId();
        bean.catalogId = resource.getCatalogId();
        bean.type = resource.getType();
        bean.desc = resource.getDesc();
        bean.name = resource.getName();
        bean.url = resource.getUrl();
        bean.statistic = resource.getStatistic();
        bean.createTime = resource.getCreateTime();
        bean.modifyTime = resource.getModifyTime();
        return bean;
    }
}

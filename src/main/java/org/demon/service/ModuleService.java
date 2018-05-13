/**
 * 项目名：admin
 * 包名：org.demon.service
 * 文件名：ModuleService
 * 日期：2018/5/12-下午4:43
 * Copyright (c) 2018
 */
package org.demon.service;

import org.demon.bean.ModuleBean;
import org.demon.bean.ModuleQuery;
import org.demon.bean.PageData;
import org.demon.mapper.ModuleMapper;
import org.demon.pojo.Module;
import org.demon.pojo.ModuleExample;
import org.demon.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 类名称：ModuleService
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/12 下午4:43
 * 修改人：
 * 修改时间：2018/5/12 下午4:43
 * 修改备注：
 */
@Service
public class ModuleService {

    @Autowired
    private ModuleMapper moduleMapper;


    public PageData<ModuleBean> selectModules(ModuleQuery query) {
        ModuleExample example = new ModuleExample();
        ModuleExample.Criteria criteria = example.createCriteria();
        if (NumberUtil.isInValidId(query.packageId)) {
            criteria.andPackageIdEqualTo(query.packageId);
        }
        PageData<ModuleBean> pageData = new PageData<>();
        pageData.count = moduleMapper.countByExample(example);
        example.setLimit(query.getLimit());
        example.setOffset(query.getOffset());
        List<Module> list = moduleMapper.selectByExample(example);
        pageData.list = Optional.ofNullable(list)
                .orElseGet(ArrayList::new)
                .stream()
                .map(this::convertModuleBean)
                .collect(Collectors.toList());
        return pageData;
    }

    private ModuleBean convertModuleBean(Module module) {
        ModuleBean bean = new ModuleBean();
        bean.id = module.getId();
        bean.name = module.getName();
        bean.icon = module.getIcon();
        return bean;
    }

}

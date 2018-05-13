/**
 * 项目名：admin
 * 包名：org.demon.service
 * 文件名：UserService
 * 日期：2018/5/11-下午11:03
 * Copyright (c) 2018
 */
package org.demon.service;

import org.demon.bean.PageData;
import org.demon.bean.User;
import org.demon.bean.UserQuery;
import org.demon.mapper.AccountMapper;
import org.demon.pojo.Account;
import org.demon.pojo.AccountExample;
import org.demon.util.NumberUtil;
import org.demon.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：UserService
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/11 下午11:03
 * 修改人：
 * 修改时间：2018/5/11 下午11:03
 * 修改备注：
 */
@Service
public class UserService {
    @Autowired
    private AccountMapper accountMapper;

    public PageData<User> selectUsers(UserQuery query) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        if (NumberUtil.isValidId(query.id)) {
            criteria.andIdEqualTo(query.id);
        }
        if (StringUtil.isNotEmpty(query.name)) {
            criteria.andUsernameLike(query.name);
        }
        PageData<User> pageData = new PageData<>();
        pageData.count = accountMapper.countByExample(example);
        List<Account> list = accountMapper.selectByExample(example);
        List<User> users = new ArrayList<>();
        list.stream().filter(a -> !StringUtil.isNull(a)).forEach(b -> {
            users.add(convert(b));
        });
        pageData.list = users;
        return pageData;
    }

    private User convert(Account b) {
        User user = new User();
        user.id = b.getId();
        user.name = b.getUsername();
        return user;
    }
}

/**
 * 项目名：admin
 * 包名：org.demon.service
 * 文件名：OrderService
 * 日期：2018/7/7-下午1:38
 * Copyright (c) 2018
 */
package org.demon.service;

import org.demon.bean.pay.ZnyBean;
import org.demon.code.ResultCodes;
import org.demon.exception.CodeException;
import org.demon.mapper.OrderMapper;
import org.demon.mapper.VideoUserMapper;
import org.demon.pojo.Order;
import org.demon.pojo.VideoUser;
import org.demon.pojo.VideoUserExample;
import org.demon.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 类名称：OrderService
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/7/7 下午1:38
 * 修改人：
 * 修改时间：2018/7/7 下午1:38
 * 修改备注：
 */
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private VideoUserMapper videoUserMapper;

    @Transactional
    public void saveZfb(ZnyBean bean) {
        String orderuid = bean.orderuid;
        String[] params = orderuid.split("_");
        String channelNo = params[0];
        Integer uid = Integer.valueOf(params[1]);
        VideoUserExample videoUserExample = new VideoUserExample();
        videoUserExample.createCriteria().andIdEqualTo(uid).andChannelNoEqualTo(channelNo);
        List<VideoUser> list = videoUserMapper.selectByExample(videoUserExample);
        if (StringUtil.isEmpty(list)) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_NOT_EXIST, "no user");
        }
        VideoUser videoUser = list.get(0);
        videoUser.setType(videoUser.getType() + 1);
        videoUserMapper.updateByPrimaryKeySelective(videoUser);
        Order order = new Order();
        order.setOrderNo(bean.orderid);
        order.setPayNo(bean.ordno);
        order.setPrice(bean.price);
        order.setRealPrice(bean.realprice);
        order.setCreateTime(new Date());
        order.setModifyTime(new Date());
        orderMapper.insertSelective(order);
    }
}

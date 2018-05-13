/**
 * 项目名：admin
 * 包名：org.demon.service
 * 文件名：PayService
 * 日期：2018/5/13-下午8:37
 * Copyright (c) 2018
 */
package org.demon.service;

import org.demon.bean.RealTimeDataBean;
import org.demon.mapper.ChannelMapper;
import org.demon.mapper.RealTimeDataMapper;
import org.demon.pojo.Channel;
import org.demon.pojo.ChannelExample;
import org.demon.pojo.RealTimeData;
import org.demon.pojo.RealTimeDataExample;
import org.demon.util.JSONUtil;
import org.demon.util.Logger;
import org.demon.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类名称：PayService
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/13 下午8:37
 * 修改人：
 * 修改时间：2018/5/13 下午8:37
 * 修改备注：
 */
@Service
public class PayService {

    private Logger logger = Logger.newInstance(PayService.class);

    @Autowired
    private RealTimeDataMapper realTimeDataMapper;
    @Autowired
    private ChannelMapper channelMapper;


    /**
     * 支付成功后的业务处理
     *
     * @param bean 查看org.demon.bean.RealTimeDataBean
     */
    public void updateRealTimeData(RealTimeDataBean bean) {
        String channelNo = bean.channelNo;
        ChannelExample channelExample = new ChannelExample();
        channelExample.createCriteria().andNoEqualTo(channelNo);
        List<Channel> channels = channelMapper.selectByExample(channelExample);
        if (StringUtil.isEmpty(channels)) {
            logger.error("支付成功后处理失败(渠道查找不到)：" + JSONUtil.obj2Json(bean));
        }
        Channel channel = channels.get(0);
        RealTimeDataExample realTimeDataExample = new RealTimeDataExample();
        realTimeDataExample.createCriteria().andChannelNoEqualTo(channelNo);
        List<RealTimeData> realTimeDatas = realTimeDataMapper.selectByExample(realTimeDataExample);
        RealTimeData realTimeData = null;
        if (StringUtil.isEmpty(realTimeDatas)) {
            realTimeData = new RealTimeData();
            realTimeData.setChannelNo(channelNo);
            if (realTimeDataMapper.insertSelective(realTimeData) <= 0) {
                logger.error("支付成功后处理失败(插入real_time_data失败)：" + JSONUtil.obj2Json(bean));
            }
        } else {
            realTimeData = realTimeDatas.get(0);
        }
        Long payPrice = bean.payPrice;
        Long channelPrice = bean.payPrice * channel.getPrecent() / 100;
        //更新统计数据
        realTimeDataMapper.updateStatistic(payPrice, channelPrice);
    }

}

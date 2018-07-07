/**
 * 项目名：admin
 * 包名：org.demon.service.pay
 * 文件名：ZnyService
 * 日期：2018/7/7-上午10:35
 * Copyright (c) 2018
 */
package org.demon.service.pay;

import org.demon.bean.pay.ZnyBean;
import org.demon.config.PayConfig;
import org.demon.exception.net.ParseBeanException;
import org.demon.exception.net.ServerIOException;
import org.demon.exception.net.ServerOtherException;
import org.demon.exception.net.ServerProtocolException;
import org.demon.exception.net.ServerTimeoutException;
import org.demon.exception.net.ServerUrlException;
import org.demon.exception.net.ServerUrlNotFoundException;
import org.demon.util.HttpUtil;
import org.demon.util.JSONUtil;
import org.demon.util.Logger;
import org.demon.util.MD5;
import org.demon.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类名称：ZnyService
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/7/7 上午10:35
 * 修改人：
 * 修改时间：2018/7/7 上午10:35
 * 修改备注：
 */
@Service
public class ZnyService {

    private static final Logger logger = Logger.newInstance(ZnyService.class);

    @Autowired
    private PayConfig payConfig;

    public ZnyBean order(ZnyBean bean) {
        ZnyBean resp = null;
        try {
            bean.orderid = createOrderNo();
            bean.key = MD5.md5(bean.getResource());
            String resource = bean.getParams();
            logger.info("======>" + resource);
            String result = HttpUtil.loadGetRequest(payConfig.getZny().getOrder() + "?" + resource);
            logger.info(result);
            resp = JSONUtil.json2Obj(result, ZnyBean.class);
        } catch (ServerIOException e) {
            e.printStackTrace();
        } catch (ServerTimeoutException e) {
            e.printStackTrace();
        } catch (ServerOtherException e) {
            e.printStackTrace();
        } catch (ServerProtocolException e) {
            e.printStackTrace();
        } catch (ServerUrlException e) {
            e.printStackTrace();
        } catch (ParseBeanException e) {
            e.printStackTrace();
        } catch (ServerUrlNotFoundException e) {
            e.printStackTrace();
        }
        return resp;
    }

    /**
     * 创建订单号
     * （废弃）生成规则： 微链商品服务规划编号 + 当前时间戳 + 随机4位字母 + 微链商品服务规划Redis
     * 订单号：年月日时分秒+4位特殊码 ： 当前时间戳 + 随机4位字母
     *
     * @return String
     */
    public String createOrderNo() {
        return System.currentTimeMillis() + StringUtil.getRandomLetterUpCase(4);
    }
}

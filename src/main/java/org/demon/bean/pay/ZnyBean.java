/**
 * 项目名：admin
 * 包名：org.demon.bean.pay
 * 文件名：znyBean
 * 日期：2018/7/7-上午9:49
 * Copyright (c) 2018
 */
package org.demon.bean.pay;

import org.demon.Constants;
import org.demon.util.StringUtil;

/**
 * 类名称：znyBean
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/7/7 上午9:49
 * 修改人：
 * 修改时间：2018/7/7 上午9:49
 * 修改备注：
 */
public class ZnyBean {
    public String uid = Constants.znyUid;
    //必填。单位：元。精确小数点后2位
    public float price;
    public float realprice;
    //必填。10001：支付宝；20001：微信支付
    public int istype = 10001;
    public String notify_url = Constants.notify;
    /**
     * 跳转网址
     */
    public String return_url;
    public String format;
    public String orderid;
    /**
     * 透传参数 channelNo_uid
     */
    public String orderuid;
    public String goodsname = StringUtil.getRandomLetterDownCase(5);
    public String key;
    public String ordno;
    public Data data;
    public int code;

    /**
     * goodsname + istype + notify_url + orderid + orderuid + price + return_url + token + uid
     */
    public String getResource() {
        return goodsname + istype + notify_url + orderid + orderuid + price + return_url + Constants.znyToken +
                uid;
    }

    public String getParams() {
        return "goodsname=" + goodsname + "&istype=" + istype + "&notify_url=" + notify_url + "&orderid=" + orderid +
                "&orderuid=" + orderuid
                + "&price=" + price + "&return_url=" + return_url + "&uid=" + uid + "&key=" + key;
    }

}



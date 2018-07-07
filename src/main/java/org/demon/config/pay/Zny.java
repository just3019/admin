/**
 * 项目名：admin
 * 包名：org.demon.config.pay
 * 文件名：Zny
 * 日期：2018/7/7-上午10:16
 * Copyright (c) 2018
 */
package org.demon.config.pay;

import org.demon.Constants;

/**
 * 类名称：Zny
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/7/7 上午10:16
 * 修改人：
 * 修改时间：2018/7/7 上午10:16
 * 修改备注：
 */
public class Zny {
    private String uid;
    private String token;
    private String order;
    private String select;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
        Constants.znyUid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        Constants.znyToken = token;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }
}

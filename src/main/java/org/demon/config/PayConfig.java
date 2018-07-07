/**
 * 项目名：admin
 * 包名：org.demon.config
 * 文件名：PayConfig
 * 日期：2018/5/13-下午8:32
 * Copyright (c) 2018
 */
package org.demon.config;

import org.demon.Constants;
import org.demon.config.pay.Zny;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 类名称：PayConfig
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/13 下午8:32
 * 修改人：
 * 修改时间：2018/5/13 下午8:32
 * 修改备注：
 */
@ConfigurationProperties(prefix = "pay")
@Component
public class PayConfig {
    private String notify;
    private Zny zny;


    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
        Constants.notify = notify;
    }

    public Zny getZny() {
        return zny;
    }

    public void setZny(Zny zny) {
        this.zny = zny;
    }
}

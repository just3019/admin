package org.demon.bean;


import org.demon.util.JSONUtil;

/**
 * Description: 基础JAVABean 定义了toString 为Json格式
 * Date: 2016/10/19 14:36
 *
 * @author Sean.xie
 */
public class BaseBean {

    @Override
    public final String toString() {
        return JSONUtil.obj2Json(this);
    }

}

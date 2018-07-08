/**
 * 项目名：admin
 * 包名：org.demon.bean
 * 文件名：ActiveUserBean
 * 日期：2018/5/14-下午9:53
 * Copyright (c) 2018
 */
package org.demon.bean;

/**
 * 类名称：ActiveUserBean
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/14 下午9:53
 * 修改人：
 * 修改时间：2018/5/14 下午9:53
 * 修改备注：
 */
public class VideoUserBean extends BaseBean {

    public Integer id;

    public String channelNo;//渠道 默认aaaaa

    public Integer type;//类型 1:普通 2:vip 3:高级vip

    public String cellModel;//ios

    public String cellVersion;//系统版本

    public Integer packageId;//1

    public String code;//激活码


}

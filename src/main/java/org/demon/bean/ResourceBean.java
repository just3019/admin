/**
 * 项目名：admin
 * 包名：org.demon.bean
 * 文件名：ResourceBean
 * 日期：2018/5/13-上午11:50
 * Copyright (c) 2018
 */
package org.demon.bean;

import java.util.Date;
import java.util.List;

/**
 * 类名称：ResourceBean
 * 类描述：  
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/13 上午11:50  
 * 修改人：  
 * 修改时间：2018/5/13 上午11:50  
 * 修改备注：      
 */
public class ResourceBean {

    public Integer id;

    public Integer catalogId;

    public Integer type;

    public String desc;

    public String name;

    public String url;

    public Integer statistic;

    public Date createTime;

    public Date modifyTime;

    public List<ResourceInfoBean> infos;
}

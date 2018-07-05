/**
 * 项目名：admin
 * 包名：org.demon.enums
 * 文件名：EnumCodeStatus
 * 日期：2018/7/5-下午10:27
 * Copyright (c) 2018
 */
package org.demon.enums;

/**
 * 类名称：EnumCodeStatus
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/7/5 下午10:27
 * 修改人：
 * 修改时间：2018/7/5 下午10:27
 * 修改备注：
 */
public enum EnumCodeStatus implements EnumValue<Integer> {

    USED(1, "已使用"), NOUSED(0, "未使用");

    private Integer value;
    private String name;


    EnumCodeStatus(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}

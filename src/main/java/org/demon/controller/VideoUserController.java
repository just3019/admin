/**
 * 项目名：admin
 * 包名：org.demon.controller
 * 文件名：ActiveUserController
 * 日期：2018/5/14-下午9:52
 * Copyright (c) 2018
 */
package org.demon.controller;

import org.demon.bean.VideoUserBean;
import org.demon.code.ResultCodes;
import org.demon.exception.CodeException;
import org.demon.mapper.VideoUserMapper;
import org.demon.service.VideoUserService;
import org.demon.util.NumberUtil;
import org.demon.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：ActiveUserController
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/14 下午9:52
 * 修改人：
 * 修改时间：2018/5/14 下午9:52
 * 修改备注：
 */
@RestController
public class VideoUserController {

    @Autowired
    private VideoUserService videoUserService;
    @Autowired
    private VideoUserMapper videoUserMapper;

    @RequestMapping(value = "videoUser/active", method = RequestMethod.POST)
    public Object active(@RequestBody VideoUserBean activeUserBean) {
        if (StringUtil.isEmpty(activeUserBean.channelNo)) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS, "channelNo");
        }
        if (NumberUtil.isInValidId(activeUserBean.packageId)) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS, "packageId");
        }
        return videoUserService.active(activeUserBean);
    }

    @RequestMapping(value = "videoUser/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable Integer id) {
        if (NumberUtil.isInValidId(id)) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS, "id");
        }
        return videoUserMapper.selectByPrimaryKey(id);
    }


}

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
import org.demon.pojo.VideoUserActive;
import org.demon.service.VideoUserService;
import org.demon.util.NumberUtil;
import org.demon.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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


    /**
     * 获取激活码
     */
    @RequestMapping(value = "code", method = RequestMethod.GET)
    public Object getCode() {
        return videoUserService.createCode();
    }

    /**
     * 激活码激活会员
     */
    @RequestMapping(value = "code/active", method = RequestMethod.POST)
    public Object codeActive(@RequestBody VideoUserBean param) {
        if (StringUtil.isEmpty(param.code)) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS, "code is null");
        }
        if (NumberUtil.isInValidId(param.id)) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_PARAMS, "id is null");
        }
        if (StringUtil.isEmpty(param.channelNo)) {
            param.channelNo = "Q00001";
        }
        videoUserService.codeActive(param);
        return null;
    }


}

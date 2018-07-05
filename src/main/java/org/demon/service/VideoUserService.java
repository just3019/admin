/**
 * 项目名：admin
 * 包名：org.demon.service
 * 文件名：ActiveUserService
 * 日期：2018/5/14-下午9:53
 * Copyright (c) 2018
 */
package org.demon.service;

import org.demon.bean.VideoUserBean;
import org.demon.code.ResultCodes;
import org.demon.enums.EnumCodeStatus;
import org.demon.exception.CodeException;
import org.demon.mapper.VideoUserActiveMapper;
import org.demon.mapper.VideoUserMapper;
import org.demon.pojo.VideoUser;
import org.demon.pojo.VideoUserActive;
import org.demon.pojo.VideoUserActiveExample;
import org.demon.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 类名称：ActiveUserService
 * 类描述：
 *
 * @author slh
 * @version 1.0.0
 * 创建时间：2018/5/14 下午9:53
 * 修改人：
 * 修改时间：2018/5/14 下午9:53
 * 修改备注：
 */
@Service
public class VideoUserService {

    @Autowired
    private VideoUserMapper videoUserMapper;
    @Autowired
    private VideoUserActiveMapper videoUserActiveMapper;


    public VideoUser active(VideoUserBean activeUserBean) {
        VideoUser videoUser = convertPojo(activeUserBean);
        if (videoUserMapper.insertSelective(videoUser) <= 0) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_BIZ, "active_error");
        }
        return videoUser;
    }

    private VideoUser convertPojo(VideoUserBean bean) {
        VideoUser videoUser = new VideoUser();
        videoUser.setChannelNo(bean.channelNo);
        videoUser.setCellModel(bean.cellModel);
        videoUser.setCellVersion(bean.cellVersion);
        videoUser.setPackageId(bean.packageId);
        videoUser.setType(1);
        return videoUser;
    }

    /**
     * 生成激活码
     */
    public VideoUserActive createCode() {
        String code = StringUtil.getRandomLetterDownCase(6);
        VideoUserActiveExample example = new VideoUserActiveExample();
        example.createCriteria().andCodeEqualTo(code);
        List<VideoUserActive> list = videoUserActiveMapper.selectByExample(example);
        while (StringUtil.isNotEmpty(list)) {
            code = StringUtil.getRandomLetterDownCase(6);
            example.clear();
            example.createCriteria().andCodeEqualTo(code);
            list = videoUserActiveMapper.selectByExample(example);
        }
        VideoUserActive active = new VideoUserActive();
        active.setCode(code);
        active.setStatus(EnumCodeStatus.NOUSED.getValue());
        if (videoUserActiveMapper.insertSelective(active) <= 0) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_BIZ, "save code error");
        }
        return active;
    }

    /**
     * 激活用户会员
     * @param param
     */
    @Transactional
    public void codeActive(VideoUserBean param) {
        VideoUserActiveExample example = new VideoUserActiveExample();
        example.createCriteria().andCodeEqualTo(param.code).andStatusEqualTo(EnumCodeStatus.NOUSED.getValue());
        List<VideoUserActive> list = videoUserActiveMapper.selectByExample(example);
        if (StringUtil.isEmpty(list)) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_NOT_EXIST, "no code");
        }
        VideoUserActive active = list.get(0);
        active.setStatus(EnumCodeStatus.USED.getValue());
        if (videoUserActiveMapper.updateByPrimaryKeySelective(active) <= 0) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_BIZ, "update code fail");
        }
        VideoUser videoUser = videoUserMapper.selectByPrimaryKey(param.id);
        videoUser.setType(videoUser.getType() + 1);
        if (videoUserMapper.updateByPrimaryKey(videoUser) <= 0) {
            throw new CodeException(ResultCodes.Code.COMMON_ERROR_BIZ, "active fail");
        }


    }
}

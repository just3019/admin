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
import org.demon.exception.CodeException;
import org.demon.mapper.VideoUserMapper;
import org.demon.pojo.VideoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

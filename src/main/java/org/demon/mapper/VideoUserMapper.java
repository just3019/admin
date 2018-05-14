package org.demon.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.demon.pojo.VideoUser;
import org.demon.pojo.VideoUserExample;

public interface VideoUserMapper {
    long countByExample(VideoUserExample example);

    int deleteByExample(VideoUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VideoUser record);

    int insertSelective(VideoUser record);

    List<VideoUser> selectByExample(VideoUserExample example);

    VideoUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VideoUser record, @Param("example") VideoUserExample example);

    int updateByExample(@Param("record") VideoUser record, @Param("example") VideoUserExample example);

    int updateByPrimaryKeySelective(VideoUser record);

    int updateByPrimaryKey(VideoUser record);
}
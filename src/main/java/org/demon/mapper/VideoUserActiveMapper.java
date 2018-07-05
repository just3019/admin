package org.demon.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.demon.pojo.VideoUserActive;
import org.demon.pojo.VideoUserActiveExample;

public interface VideoUserActiveMapper {
    long countByExample(VideoUserActiveExample example);

    int deleteByExample(VideoUserActiveExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VideoUserActive record);

    int insertSelective(VideoUserActive record);

    List<VideoUserActive> selectByExample(VideoUserActiveExample example);

    VideoUserActive selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VideoUserActive record, @Param("example") VideoUserActiveExample
            example);

    int updateByExample(@Param("record") VideoUserActive record, @Param("example") VideoUserActiveExample example);

    int updateByPrimaryKeySelective(VideoUserActive record);

    int updateByPrimaryKey(VideoUserActive record);
}
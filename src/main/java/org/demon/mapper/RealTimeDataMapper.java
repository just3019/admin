package org.demon.mapper;

import org.apache.ibatis.annotations.Param;
import org.demon.pojo.RealTimeData;
import org.demon.pojo.RealTimeDataExample;

import java.util.List;

public interface RealTimeDataMapper {
    long countByExample(RealTimeDataExample example);

    int deleteByExample(RealTimeDataExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RealTimeData record);

    int insertSelective(RealTimeData record);

    List<RealTimeData> selectByExample(RealTimeDataExample example);

    RealTimeData selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RealTimeData record, @Param("example") RealTimeDataExample example);

    int updateByExample(@Param("record") RealTimeData record, @Param("example") RealTimeDataExample example);

    int updateByPrimaryKeySelective(RealTimeData record);

    int updateByPrimaryKey(RealTimeData record);

    void updateStatistic(@Param("payPrice") Long payPrice, @Param("channelPrice") Long channelPrice);
}
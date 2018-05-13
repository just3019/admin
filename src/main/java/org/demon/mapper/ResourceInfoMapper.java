package org.demon.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.demon.pojo.ResourceInfo;
import org.demon.pojo.ResourceInfoExample;

public interface ResourceInfoMapper {
    long countByExample(ResourceInfoExample example);

    int deleteByExample(ResourceInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ResourceInfo record);

    int insertSelective(ResourceInfo record);

    List<ResourceInfo> selectByExample(ResourceInfoExample example);

    ResourceInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ResourceInfo record, @Param("example") ResourceInfoExample example);

    int updateByExample(@Param("record") ResourceInfo record, @Param("example") ResourceInfoExample example);

    int updateByPrimaryKeySelective(ResourceInfo record);

    int updateByPrimaryKey(ResourceInfo record);
}
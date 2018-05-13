package org.demon.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.demon.pojo.Package;
import org.demon.pojo.PackageExample;

public interface PackageMapper {
    long countByExample(PackageExample example);

    int deleteByExample(PackageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Package record);

    int insertSelective(Package record);

    List<Package> selectByExample(PackageExample example);

    Package selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Package record, @Param("example") PackageExample example);

    int updateByExample(@Param("record") Package record, @Param("example") PackageExample example);

    int updateByPrimaryKeySelective(Package record);

    int updateByPrimaryKey(Package record);
}
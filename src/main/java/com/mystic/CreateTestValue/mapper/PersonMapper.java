package com.mystic.CreateTestValue.mapper;

import com.mystic.CreateTestValue.entity.PersonEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mystic
 * @date 2022/5/1 10:01
 */
@Mapper
public interface PersonMapper {
    /**
     * 单笔 person 入表
     *
     * @param person 入表对象
     * @return 入表明细数目
     */
    int insertPerson(PersonEntity person);

    /**
     * 批量person入库
     *
     * @param list person 列表
     */
    void batchInsertPerson(@Param("list") List<PersonEntity> list);

    /**
     * 查询最新入库的数据(多个基本类型参数必须使用@param注解,不然找不到对应的参数)
     *
     * @return person list
     */
    List<PersonEntity> queryPerson();
}

package com.mystic.CreateTestValue.service;


import com.mystic.CreateTestValue.entity.PersonEntity;
import com.mystic.CreateTestValue.utils.PageUtil;

import java.util.List;

/**
 * @author mystic
 * @date 2022/4/30 21:02
 */
public interface PersonService {
    /**
     * 将创建的person对象插入表中
     *
     * @param count 入表数
     * @return 入表条数
     */
    int insertPerson(Integer count);

    /**
     * 查询插入的数据反显给前
     * @return person list
     */
    List<PersonEntity> queryPerson();
}

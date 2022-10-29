package com.mystic.CreateTestValue.service.impl;

import com.github.javafaker.Faker;
import com.mystic.CreateTestValue.entity.PersonEntity;
import com.mystic.CreateTestValue.mapper.PersonMapper;
import com.mystic.CreateTestValue.service.PersonService;
import com.mystic.CreateTestValue.utils.ListUtil;
import com.mystic.CreateTestValue.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apifan.common.random.source.PersonInfoSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author mystic
 * @date 2022/4/30 21:04
 */
@Service
public class PersonServiceImpl implements PersonService {

    final PersonMapper personMapper;

    @Autowired
    public PersonServiceImpl(PersonMapper personMapper1) {
        this.personMapper = personMapper1;
    }

    @Override
    public int insertPerson(Integer count) {
        Faker faker = new Faker(Locale.CHINA);
        List<PersonEntity> list = new ArrayList<>();
        SimpleDateFormat sft = new SimpleDateFormat("yyyyMMdd");
        String female = "F";
        String male = "M";
        int insertCount = 0;
        try {
            for (int i = 0; i < count; i++) {
                PersonEntity person = new PersonEntity();
                person.setAddr(faker.address().streetAddress());
                person.setIdNo(i % 2 == 0 ?
                        PersonInfoSource.getInstance().randomFemaleIdCard("江苏省", 1, 105) :
                        PersonInfoSource.getInstance().randomMaleIdCard("江苏省", 1, 99));
                person.setBirth(person.getIdNo().substring(6, 14));
                person.setIdExpiryDate(sft.format(faker.date().between(sft.parse("20301231"), sft.parse("20991231"))));
                person.setIdType("1");
                person.setPhoneNo(faker.phoneNumber().cellPhone());
                person.setSex(i % 2 == 0 ? female : male);
                person.setWorkdate(sft.format(Calendar.getInstance().getTime()));
//                根据身份证号第17位判断性别获取对应性别的姓名
                person.setName(i % 2 == 0 ?
                        PersonInfoSource.getInstance().randomFemaleChineseName() :
                        PersonInfoSource.getInstance().randomMaleChineseName());
                list.add(person);
            }
//            mysql入表的sql最大不能超过4M,这里将批量入库的时候取每次1000笔
            List<List<PersonEntity>> splitList = ListUtil.splitListByQuantity(list, 1000);
            for (List<PersonEntity> personList : splitList
            ) {
                personMapper.batchInsertPerson(personList);
                insertCount += personList.size();
            }
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        return insertCount;
    }

    @Override
    public List<PersonEntity> queryPerson() {
        return personMapper.queryPerson();
    }
}

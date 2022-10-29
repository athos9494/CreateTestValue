package com.mystic.CreateTestValue.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mystic.CreateTestValue.entity.PersonEntity;
import com.mystic.CreateTestValue.service.PersonService;
import com.mystic.CreateTestValue.service.impl.PersonServiceImpl;
import com.mystic.CreateTestValue.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author mystic
 * @date 2022/4/30 20:40
 */
@Controller
public class PersonController {
    private static final String READ_MSG = "点击这里查看生成的数据";
    final PersonService personService;

    @Autowired
    public PersonController(PersonServiceImpl personService) {
        this.personService = personService;
    }


    @GetMapping("/createPerson")
    public String createPersonTestValue(@RequestParam Integer count, Model model) {
        int success = personService.insertPerson(count);
        String rtn = "执行成功" + success + "条";
        model.addAttribute("msg", rtn);
        model.addAttribute("success", success);
        model.addAttribute("readmsg", READ_MSG);
        return "person";
    }

    @RequestMapping("/queryPerson")
    public String queryPerson(@RequestParam int pageNum, @RequestParam int pageSize, Model model) {


        PageHelper.startPage(pageNum, pageSize);
        List<PersonEntity> personList = personService.queryPerson();
        PageInfo<PersonEntity> pageInfo= new PageInfo<>(personList);
        model.addAttribute("pageInfo", pageInfo);
        return "/detail";
    }
}
package com.mystic.CreateTestValue.controller;

import com.mystic.CreateTestValue.enums.IndexCategoryEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mystic
 * @date 2022/5/1 17:49
 */
@Controller
public class IndexController {
    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("/choose")
    public String indexAction(@RequestParam String chooseParam, Model model) {
        if (Integer.parseInt(chooseParam) == IndexCategoryEnum.CREATE_PERSON.getCate()) {
            return "person";
        }
        else if (Integer.parseInt(chooseParam) == IndexCategoryEnum.FILE_TRANSFER.getCate()) {
            return "transfer";
        } else if (Integer.parseInt(chooseParam) == IndexCategoryEnum.PDF_CONVERT.getCate()){
            return "pdfFile";
        }else {
            model.addAttribute("msg", "无法找到对应应用,请确认");
            return "index";
        }
    }
}

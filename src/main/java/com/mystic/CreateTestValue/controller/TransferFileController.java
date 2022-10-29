package com.mystic.CreateTestValue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author mystic
 * @date 2022/8/16 22:38
 */
@Controller
@RequestMapping("transferFile")
public class TransferFileController {
    public static final String PATH = "/";

    @RequestMapping("/transfer")
    public String transfer(@RequestParam("filePath") String filePath, MultipartFile mufile, Model model){
        if (mufile == null){
            model.addAttribute("msg", "接收文件为空,请联系管理员查看");
            return "transfer";
        }
        if (filePath == null){
            model.addAttribute("msg", "路径为空,请确认");
            return "transfer";
        }else if (!filePath.endsWith (PATH)){
            filePath = filePath.concat(PATH);
        }

        try {
            String filename  = mufile.getOriginalFilename();

            System.out.println(filename);

            File file = new File(filePath + filename);
            if (file.exists()) {
                file.delete();
            }

            mufile.transferTo(file);
            model.addAttribute("msg", "文件传输成功");
        }catch (IOException e){
            e.printStackTrace();
        }

        return "transfer";
    }
}

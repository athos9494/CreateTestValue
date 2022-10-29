package com.mystic.CreateTestValue.controller;

import com.mystic.CreateTestValue.entity.FileConvertInfoEntity;
import com.mystic.CreateTestValue.service.FileConvertInfoService;
import com.mystic.CreateTestValue.service.PdfService;
import com.mystic.CreateTestValue.service.impl.FileConvertInfoServiceImpl;
import com.mystic.CreateTestValue.service.impl.PdfServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;


/**
 * @author mystic
 * @date 2022/8/26 22:03
 */
@Controller
@RequestMapping("/pdfUtil")
public class PdfConvertController {

    private static final String SUFFIX = ".pdf";

    final PdfService pdfService;
    final FileConvertInfoService fileConvertInfoService;

    @Autowired
    public PdfConvertController(PdfServiceImpl pdfService, FileConvertInfoServiceImpl service) {
        this.pdfService = pdfService;
        this.fileConvertInfoService = service;
    }

    @RequestMapping("/pdfConvert")
    public String convert(@RequestParam("function") String function, MultipartFile pdfFile, Model model) {
        String filename = pdfFile.getOriginalFilename();

        if ((!StringUtils.isEmpty(function)) && Integer.parseInt(function) == 0) {
            if (pdfFile.isEmpty() || filename == null) {
                model.addAttribute("msg", "文件大小为空");
                return "pdfFile";
            } else if (!Objects.equals(filename.substring(filename.lastIndexOf(".")), SUFFIX)) {
                model.addAttribute("msg", "文件必须为PDF格式");
                return "pdfFile";
            }
//            初始化转换明细表
            fileConvertInfoService.init(new FileConvertInfoEntity(filename, "", 2));
//            pdf文件转换doc
            pdfService.pdfConvertToWord(pdfFile);
            model.addAttribute("msg", "接收文件成功,正在转换中");
            return "pdfFile";
        } else {
            model.addAttribute("msg", "转换失败, 暂不支持此功能");
        }
        return "pdfFile";
    }

    @RequestMapping("/queryRst")
    public String queryRst(Model model) {
        List<FileConvertInfoEntity> fileList = fileConvertInfoService.query();
        if (fileList.size() <= 0) {
            model.addAttribute("msg", "未查询到文件");
            return "pdfFile";
        } else {
            model.addAttribute("fileList", fileList);
            return "pdfConvertDetail";
        }
    }

    @RequestMapping("/downRstFile")
    public String downRstFile(@RequestParam("id") int id, Model model) {
//        获取文件名
        String sendFilename = fileConvertInfoService.getFilenameById(id);
        String url = pdfService.getUrl(sendFilename);
        model.addAttribute("fileUrl", url);
        return "download";
    }
}

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

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
    private static final String HOME_PATH = System.getProperty("user.home");
    private static final String RECV_PATH = "/workspace/file/recv/";


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
            File recvFile = new File(HOME_PATH + RECV_PATH + filename);
//            pdf文件转换doc
            try (InputStream is = pdfFile.getInputStream(); OutputStream os = new FileOutputStream(recvFile)){
                byte[] buf = new byte[1024];
                int len;
                while ((len = is.read(buf)) != -1){
                    os.write(buf, 0, len);
                }
                os.flush();
//           异步方法,无法获取multipart file,直接写成file传递
                pdfService.pdfConvertToWord(recvFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    public String downRstFile(@RequestParam("filename") String filename, Model model, HttpServletResponse response) {
//        获取文件名
        String sendFilename = fileConvertInfoService.getFilenameByRecvName(filename);
//        文件路径最好配在表里
        String sendPath = System.getProperty("user.home") + "/workspace/file/send/" + sendFilename;
        if (StringUtils.isEmpty(sendPath)){
            model.addAttribute("msg", "结果文件未生成或生成失败");
            return "404";
        }
        File file = new File(sendPath);
        if (!file.exists()) {
            model.addAttribute("msg", "结果文件不存在");
            return "404";
        }
        byte[] buf = new byte[1024];
        int len;
        try (InputStream is = new FileInputStream(file); OutputStream os = response.getOutputStream()) {
            response.setContentType("application/force-download");
            response.addHeader
                    ("Content-Disposition", "attachment; filename=" + URLEncoder.encode(sendFilename, "UTF-8"));
            response.setContentLength((int) file.length());
            while ((len = is.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
            os.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/download")
    public String download() {
        return "download";
    }
}

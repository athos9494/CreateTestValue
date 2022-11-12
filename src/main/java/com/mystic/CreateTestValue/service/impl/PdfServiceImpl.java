package com.mystic.CreateTestValue.service.impl;

import com.aspose.pdf.exceptions.PdfException;
import com.mystic.CreateTestValue.service.FileConvertInfoService;
import com.mystic.CreateTestValue.service.PdfService;
import com.mystic.CreateTestValue.utils.PdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;


/**
 * @author mystic
 * @date 2022/8/26 22:33
 */
@Service
public class PdfServiceImpl implements PdfService {
    private static final String HOME_PATH = System.getProperty("user.home");
    private static final String SEND_PATH = "/workspace/file/send";

    final FileConvertInfoService infoService;

    @Autowired
    public PdfServiceImpl(FileConvertInfoService infoService) {
        this.infoService = infoService;
    }


    @Override
    @Async
    public void pdfConvertToWord(File recvFile) {
        String recvFilePath = recvFile.getAbsolutePath();
        String filename = recvFile.getName();
        String sendFilePath = HOME_PATH.concat(SEND_PATH).concat("/");
        String sendName = UUID.randomUUID().toString().replaceAll("-", "") + filename.replace(".pdf", "").concat(".doc");
        String sendFileName = sendFilePath.concat(sendName);
        try {
//            文件转换成doc
            PdfUtil.pdfToDoc(recvFilePath, sendFileName);
            infoService.update(filename, sendName, 1);
        } catch (PdfException e) {
            infoService.update(filename, "", 0);
            throw new PdfException("文件转换失败");
        } catch (Exception e) {
            infoService.update(filename, "", 0);
            e.printStackTrace();
        }
    }
}

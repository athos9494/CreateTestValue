package com.mystic.CreateTestValue.service.impl;

import com.aspose.pdf.exceptions.PdfException;
import com.mystic.CreateTestValue.service.FileConvertInfoService;
import com.mystic.CreateTestValue.service.PdfService;
import com.mystic.CreateTestValue.utils.FileUtil;
import com.mystic.CreateTestValue.utils.PdfUtil;
import com.mystic.CreateTestValue.utils.S3Util;
import com.mystic.CreateTestValue.utils.SimpleDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;


/**
 * @author mystic
 * @date 2022/8/26 22:33
 */
@Service
public class PdfServiceImpl implements PdfService {
    private static final String HOME_PATH = System.getProperty("user.home");
    private static final String RECV_PATH = "/workspace/file/recv";
    private static final String SEND_PATH = "/workspace/file/send";

    final FileConvertInfoService infoService;

    @Autowired
    public PdfServiceImpl(FileConvertInfoService infoService) {
        this.infoService = infoService;
    }


    @Override
    @Async
    public void pdfConvertToWord(MultipartFile file) {
        String filename =  file.getOriginalFilename();
        String recvFileName = HOME_PATH.concat(RECV_PATH).concat("/").concat(filename);
        String sendFilePath = HOME_PATH.concat(SEND_PATH).concat("/");
        String sendName = UUID.randomUUID().toString().replaceAll("-", "") + filename.replace(".pdf", "").concat(".doc");
        String sendFileName = sendFilePath.concat(sendName);
        try {
//            文件保存到本地
            FileUtil.byteToFile(file.getBytes(), recvFileName);
//            文件转换成doc
            PdfUtil.pdfToDoc(recvFileName, sendFileName);
            infoService.update(filename, sendName, 1);
        } catch (PdfException e) {
            infoService.update(filename, "", 0);
            throw new PdfException("文件转换失败");
        } catch (Exception e) {
            infoService.update(filename, "", 0);
            e.printStackTrace();
        }
    }

    @Override
    public String getUrl(String filename) {
//        桶配置,一般放在配置表里
        String publicAccessKey = "AKIA45K5AWB2H3L4LL42";
        String privateAccessKey = "X2R6cG1HHjNWw/uDsCd0fM8p2nTAROSr34nx4UCM";
        String bucketName = "mystic-test-bucket";
        String remotePath = "image/demo/" + filename;
        String filepath = HOME_PATH.concat(SEND_PATH).concat("/").concat(filename);
        return S3Util.uploadByPreSignUrl(publicAccessKey, privateAccessKey, bucketName, remotePath, filepath);
    }
}

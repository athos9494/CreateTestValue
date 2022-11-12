package com.mystic.CreateTestValue.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * @author mystic
 * @date 2022/8/26 22:25
 */
public interface PdfService {
    /**
     * 将pdf文件转换成word
     *
     * @param file 文件
     */
    void pdfConvertToWord(File file);
}

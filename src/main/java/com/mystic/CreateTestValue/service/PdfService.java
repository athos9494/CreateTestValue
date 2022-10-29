package com.mystic.CreateTestValue.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author mystic
 * @date 2022/8/26 22:25
 */
public interface PdfService {
    /**
     * 将pdf文件转换成word
     *
     * @param file 接收到的网络文件
     */
    void pdfConvertToWord(MultipartFile file);

    /**
     * upload file , return url
     *
     * @param filename filename
     * @return url
     */
    String getUrl(String filename);
}

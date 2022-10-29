package com.mystic.CreateTestValue.utils;

import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author mystic
 * @date 2022/9/3 21:43
 */
public class PdfUtil {
    public static void pdfToDoc(String pdfFilePath, String sendFilepath) throws Exception {
        File file = new File(sendFilepath);
        if (!file.exists()){
            boolean isCreate = file.createNewFile();
            if (!isCreate){
                throw new Exception("文件创建失败");
            }
        }
// 同级目录下建立一个word文档
        FileOutputStream out = new FileOutputStream(sendFilepath);
//        加载pdf文件
        Document doc = new Document(pdfFilePath);
//        pdf转换
        doc.save(out, SaveFormat.DocX);
        out.close();
    }
}

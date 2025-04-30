package com.mystic.CreateTestValue.utils;

import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import com.itextpdf.text.Paragraph;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author mystic
 * @date 2022/9/3 21:43
 */
public class PdfUtil {
    private static final Logger log = LoggerFactory.getLogger(PdfUtil.class);

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

    public static void wordToPdf(String inputFilepath, String outputFilepath) {
        com.itextpdf.text.Document pdfDocument = null;
        PdfWriter writer = null;
        try (FileInputStream fis = new FileInputStream(inputFilepath);
             XWPFDocument document = new XWPFDocument(fis)){
//            创建 PDF 文档
            pdfDocument = new com.itextpdf.text.Document();
            writer = PdfWriter.getInstance(pdfDocument, Files.newOutputStream(Paths.get(outputFilepath)));
            pdfDocument.open();
//            遍历 Word 文档的段落并添加到 PDF 文档
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                pdfDocument.add(new Paragraph(paragraph.getText()));
            }
            System.out.println("Word 转换为 PDF 成功！");
        } catch (Exception e) {
            log.info("Word 转换为 PDF 失败！" , e);
        } finally {
//            关闭文档
            try {
                if (writer != null && !writer.isCloseStream()) {
                    writer.close();
                }
                if (pdfDocument != null) {
                    pdfDocument.close();
                }
            } catch (Exception e) {
                log.info("关闭资源失败！", e);
            }
        }
    }

    public static void main(String[] args) {
        String inputFilepath = "/Users/mystic/Desktop/离职申请书.docx";
        String outputFilepath = "/Users/mystic/Desktop/离职申请书.pdf";
        wordToPdf(inputFilepath, outputFilepath);
    }
}

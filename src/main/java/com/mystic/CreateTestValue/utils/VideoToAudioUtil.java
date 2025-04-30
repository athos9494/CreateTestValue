package com.mystic.CreateTestValue.utils;

import ws.schild.jave.*;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.nio.file.FileSystemException;

/**
 * @author mystic
 * @date 2024/2/2 19:44
 */
public class VideoToAudioUtil {

    private static final String OUT_FORMAT = "MP3";

    /**
     * 获得转化后的文件名
     * @param sourceFilePath : 源视频文件路径
     */
    public static String  getNewFileName(String sourceFilePath) {
        File source = new File(sourceFilePath);
        String fileName=source.getName().substring(0, source.getName().lastIndexOf("."));
        return fileName+"."+OUT_FORMAT;
    }
    /**
     * 转化音频格式
     * @param sourceFilePath : 源视频文件路径(包含文件名)
     * @param targetFilePath : 目标音乐文件(不包含文件名)
     */
    public static void transform(String sourceFilePath, String targetFilePath) throws FileSystemException {
        File source = new File(sourceFilePath);
        File target = new File(targetFilePath+getNewFileName(sourceFilePath));
        if (source.isDirectory()&&target.isFile()){
            throw new FileSystemException("什么勾八乱送参数!");
        }
        // 设置音频属性
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec(null);
        // 设置转码属性
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat(OUT_FORMAT);
        attrs.setAudioAttributes(audio);
        try {
            // 音频转换格式类
            Encoder encoder = new Encoder();
            MultimediaObject mediaObject=new MultimediaObject(source);
            encoder.encode(mediaObject, target, attrs);
            System.out.println("转换已完成...");
        }  catch (EncoderException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        
    }
}

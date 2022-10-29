package com.mystic.CreateTestValue.utils;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

/**
 * @author mystic
 * @date 2022/1/12 23:38
 */
@Component
public class S3Util {

    public static String uploadTest(String accessKeyPublic, String accessKeyPrivate, String bucketName, String remotePath, String localPath) {
        StopWatch sw = new StopWatch("亚马逊S3上传文件耗时");
        sw.start("执行上传操作");
        StrTools.chkStr(accessKeyPublic, "公钥");
        StrTools.chkStr(accessKeyPrivate, "私钥");
        StrTools.chkStr(bucketName, "桶名");
        StrTools.chkStr(remotePath, "远程路径");
        StrTools.chkStr(localPath, "本地路径");
        String rst = null;
        try {
            InputStream is = new FileInputStream(localPath);
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(is.available());
            AmazonS3 s3 = connectS3Client(accessKeyPublic, accessKeyPrivate);
            //        设置预签名过期时间
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
//        设置过期时间一个小时
            expTimeMillis += 60 * 60 * 1000;
            expiration.setTime(expTimeMillis);
            PutObjectRequest putRequest = new PutObjectRequest(bucketName, remotePath,is ,meta );
            PutObjectResult result = s3.putObject(putRequest);
            rst = getPreSignedUrlForUpdate(s3, bucketName, remotePath);
            sw.stop();
            System.out.println("亚马逊文件上传耗时: " + sw.getTotalTimeMillis() + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }
    /**
     * 使用预签名上传文件到桶中
     *
     * @param accessKeyPublic  公钥
     * @param accessKeyPrivate 私钥
     * @param bucketName       桶名
     * @param remotePath       远程路径
     * @param localPath        本地路径
     * @return 是否成功上传
     */
    public static String uploadByPreSignUrl(String accessKeyPublic, String accessKeyPrivate, String bucketName, String remotePath, String localPath) {
        String rst = null;
        long start = Calendar.getInstance().getTimeInMillis();
        try {
            AmazonS3 s3 = connectS3Client(accessKeyPublic, accessKeyPrivate);
            //        设置预签名过期时间
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
//        设置过期时间一个小时
            expTimeMillis += 60 * 60 * 1000;
            expiration.setTime(expTimeMillis);
//        创建预签名url
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, remotePath)
                    .withMethod(HttpMethod.PUT)
                    .withExpiration(expiration);
            URL preSignedUrl = s3.generatePresignedUrl(generatePresignedUrlRequest);
//        创建连接并用此连接使用预签名url上传对象
            HttpURLConnection urlConnection = (HttpURLConnection) preSignedUrl.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("PUT");
            OutputStreamWriter outStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            String uploadStr = FileUtil.fileToStr(localPath);
            outStreamWriter.write(uploadStr);
            outStreamWriter.close();
//            检查 HTTP 响应代码。完成上传并使对象可用
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                //            获取预签名url
                rst = getPreSignedUrlForUpdate(s3, bucketName, remotePath);
                System.out.println("上传文件耗时: " + (Calendar.getInstance().getTimeInMillis() - start) + "ms");
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }

    /**
     * 根据文件url 读取网络文件内容(测试或者明确知道文件内容很小才会使用)
     *
     * @param preSignedUrl 文件url
     * @return 文件内容
     */
    public static String readInternetFile(String preSignedUrl) {
        String rst = null;
        try {

            URL url = new URL(preSignedUrl);
//            打开链接
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
//            输入输出流采用字节流
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setRequestProperty("Charset", "UTF-8");
            httpUrlConnection.setRequestProperty("Connection", "close");
            httpUrlConnection.setConnectTimeout(5000);
            httpUrlConnection.setReadTimeout(5000);
            httpUrlConnection.connect();
            if (httpUrlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            InputStream inputStream = httpUrlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
//            line 保存每行数据
            String line;
            //            判断是否读完数据
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            rst = stringBuilder.toString();
            bufferedReader.close();
            inputStream.close();
            httpUrlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rst;
    }

    /**
     * 根据文件url下载网络文件到本地
     *
     * @param localFile    保存到本地的文件全路径
     * @param preSignedUrl 文件url
     * @return 是否下载成功
     */
    public static boolean downloadFileUrl(String localFile, String preSignedUrl) {

        boolean isDownload = false;
        try {
            URL url = new URL(preSignedUrl);
//            打开链接
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
//            输入输出流采用字节流
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setRequestProperty("Charset", "UTF-8");
            httpUrlConnection.setRequestProperty("Connection", "close");
            httpUrlConnection.setConnectTimeout(5000);
            httpUrlConnection.setReadTimeout(5000);
            httpUrlConnection.connect();

            InputStream inputStream = httpUrlConnection.getInputStream();
            BufferedInputStream buffer = new BufferedInputStream(inputStream);
            byte[] bytes = new byte[1024];

//            将桶中读取的流写入到文件中去
            File file = new File(localFile);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            OutputStream out = new FileOutputStream(file);
            int size;
            while ((size = buffer.read(bytes)) != -1) {
                out.write(bytes, 0, size);
            }
//            刷新提交流关闭资源
            buffer.close();
            out.flush();
            out.close();
            httpUrlConnection.disconnect();
            isDownload = true;
        } catch (Exception e) {
            System.out.println(e.getMessage() == null ? "下载异常" : e.getMessage());
        }
        return isDownload;
    }

    /**
     * 生成amazon S3 连接器
     *
     * @param accessKeyPublic  公钥
     * @param accessKeyPrivate 私钥
     * @return amazon S3 连接器
     */
    private static AmazonS3 connectS3Client(String accessKeyPublic, String accessKeyPrivate) {
        BasicAWSCredentials clientDetails = new BasicAWSCredentials(accessKeyPublic, accessKeyPrivate);
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setProtocol(Protocol.HTTP);
//        AWSS3V4SignerType单次上传,S3SignerType 分片上传使用
        clientConfiguration.withSignerOverride("AWSS3V4SignerType")
//                .withSignerOverride("S3SignerType")
        ;
        //        通过url连接桶时set即可
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(clientDetails))
                .withClientConfiguration(clientConfiguration)
//                地区设置成桶所在的区域,测试桶在新加坡
                .withRegion("ap-southeast-1")
                .disableChunkedEncoding()
                .build()
//        通过域名连接桶
//                .setEndpoint("")
                ;
    }

    /**
     * 获取可供别人下载的共享url。ps：上传文件时生成的预签名url无法用来下载。
     *
     * @param s3Client   amazon S3客户端连接
     * @param bucketName 桶名
     * @param remotePath 远程路径
     * @return 共享的url，可供他人下载
     */
    private static String getPreSignedUrlForUpdate(AmazonS3 s3Client, String bucketName, String remotePath) {
        //        设置预签名过期时间
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
//        设置过期时间一个小时
        expTimeMillis += 60 * 60 * 1000;
        expiration.setTime(expTimeMillis);
//        创建预签名url
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, remotePath)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        URL preSignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return preSignedUrl.toString();
    }
}

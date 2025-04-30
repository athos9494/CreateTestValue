# 工程简介

# 延伸阅读

## 破解aspose-pdf

执行PDFJarCrack,用生成的aspose-pdf-21.8.cracked.jar 替换aspose-pdf-21.8.jar 即可

## 配置文件密码加密jasypt注意事项
1. 依赖jasypt-spring-boot-starter 3.0.0以下需要加上EnableEncryptableProperties注解,以上版本不需要
2. 3.0.0以上版本配置栏中需要加上jasypt.encryptor.iv-generator-classname的参数
- java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="zzzs12138"  password="decode"  algorithm=PBEWithMD5AndDES
input-明文密码
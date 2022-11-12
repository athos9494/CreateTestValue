package com.mystic.CreateTestValue.mapper;

import com.mystic.CreateTestValue.entity.FileConvertInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mystic
 * @date 2022/9/5 00:00
 */
@Mapper
public interface FileConvertInfoMapper {
    /**
     * 登记待处理的文件
     *
     * @param fileConvertInfoEntity 实体类
     */
    void initFileInfo(FileConvertInfoEntity fileConvertInfoEntity);

    /**
     * 转换完成后登记待发送文件名
     * @param recvFilename 收到的文件名
     * @param sendFilename 发送的文件名
     * @param status       状态
     * @return 执行影响条数
     */
    int updateSendFile(@Param("recv") String recvFilename, @Param("send") String sendFilename, @Param("status") int status);

    /**
     * 查询文件转换是否处理完成
     *
     * @return list
     */
    List<FileConvertInfoEntity> queryStatus();

    /**
     * query filename by id
     *
     * @param id id
     * @return filename
     */
    String getFilenameById(@Param("id") int id);

    void deleteByRecv(@Param("filename") String filename);

    String getFilenameByRecvName(@Param("filename") String filename);
}

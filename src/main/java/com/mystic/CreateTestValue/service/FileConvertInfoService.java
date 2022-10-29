package com.mystic.CreateTestValue.service;

import com.mystic.CreateTestValue.entity.FileConvertInfoEntity;

import java.util.List;

/**
 * @author mystic
 * @date 2022/9/6 22:59
 */
public interface FileConvertInfoService {
    /**
     * 初始化登记文件处理
     * @param fileConvertInfoEntity 初始化信息
     */
    void init(FileConvertInfoEntity fileConvertInfoEntity);

    /**
     * 更新文件处理状态
     *
     * @param recvName recv
     * @param sendName send
     * @param status   status
     * @return 影响条数
     */
    int update(String recvName, String sendName, int status);

    /**
     * 查询文件处理状态
     * @return 查询到的状态
     */
    List<FileConvertInfoEntity> query();

    /**
     * query filename by id
     *
     * @param id id
     * @return 文件名
     */
    String getFilenameById(int id);
}

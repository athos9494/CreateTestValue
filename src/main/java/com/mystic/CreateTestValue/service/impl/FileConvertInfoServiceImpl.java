package com.mystic.CreateTestValue.service.impl;

import com.mystic.CreateTestValue.entity.FileConvertInfoEntity;
import com.mystic.CreateTestValue.mapper.FileConvertInfoMapper;
import com.mystic.CreateTestValue.service.FileConvertInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mystic
 * @date 2022/9/6 23:15
 */
@Service
public class FileConvertInfoServiceImpl implements FileConvertInfoService {
    final FileConvertInfoMapper mapper;

    @Autowired
    public FileConvertInfoServiceImpl(FileConvertInfoMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void init(FileConvertInfoEntity fileConvertInfoEntity) {
        mapper.initFileInfo(fileConvertInfoEntity);
    }

    @Override
    public int update(String recvName, String sendName, int status) {
        return mapper.updateSendFile(recvName, sendName,status);
    }

    @Override
    public List<FileConvertInfoEntity> query() {
        return mapper.queryStatus();
    }

    @Override
    public String getFilenameById(int id) {
        return mapper.getFilenameById(id);
    }
}

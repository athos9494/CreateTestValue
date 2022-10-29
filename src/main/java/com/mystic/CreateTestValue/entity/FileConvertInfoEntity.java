package com.mystic.CreateTestValue.entity;

/**
 * @author mystic
 * @date 2022/9/5 00:37
 */
public class FileConvertInfoEntity {
    private int id;
    private String recv;
    private String send;
    private int status;

    public FileConvertInfoEntity() {
    }

    public FileConvertInfoEntity(String recv) {
        this.recv = recv;
    }

    public FileConvertInfoEntity(String recv, String send, int status) {
        this.recv = recv;
        this.send = send;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecv() {
        return recv;
    }

    public void setRecv(String recv) {
        this.recv = recv;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

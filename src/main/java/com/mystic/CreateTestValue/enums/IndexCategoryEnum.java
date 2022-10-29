package com.mystic.CreateTestValue.enums;

/**
 * @author mystic
 */
public enum IndexCategoryEnum {
    /**
     * 创建文件
     */
    CREATE_PERSON(0),
    /**
     * 文件传输到指定目录
     */
    FILE_TRANSFER(1),
    /**
     * PDF文件转换成其他格式
     */
    PDF_CONVERT(3);
    private int cate;

    IndexCategoryEnum(int cate) {
        this.cate = cate;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }
}

package com.mystic.CreateTestValue.utils;

import java.util.List;

/**
 * @author mystic
 * @date 2022/8/19 23:12
 */
public class PageUtil {

//    0 表示没有,  1 表示有

    private int startIndex = 1;
    private int pageSize = 10;
    private int totalNum;
    private int totalPage;
    private int hasFrontPage;
    private int hasPrePage;
    private int hasNextPage;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        if (startIndex > 1) {
            this.startIndex = startIndex;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize > 10) {
            this.pageSize = pageSize;

        }
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getHasFrontPage() {
        return hasFrontPage;
    }

    public void setHasFrontPage(int hasFrontPage) {
        this.hasFrontPage = hasFrontPage;
    }

    public int getHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(int hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public int getHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(int hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}

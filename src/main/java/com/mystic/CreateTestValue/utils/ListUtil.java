package com.mystic.CreateTestValue.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mystic
 * @date 2022/5/1 10:34
 */
@Component
public class ListUtil {
    /**
     * 将一个列表拆分成固定大小的数个小列表
     *
     * @param list     需要拆分的列表
     * @param quantity 拆分结果列表size
     * @param <T>      数组列表数据类型
     * @return 包含所有拆分列表的二维列表
     */
    public static <T> List<List<T>> splitListByQuantity(List<T> list, int quantity) {
        if (list == null || list.size() == 0) {
            return null;
        }
        int minSplitNum = 2;
        if (quantity <= minSplitNum) {
            new IllegalArgumentException("输入的列表拆分数量不能少于2");
        }
        List<List<T>> resList = new ArrayList<>();
        int count = 0;
        while (count < list.size()) {
            resList.add(new ArrayList<>(list.subList(count, Math.min((count + quantity), list.size()))));
            count += quantity;
        }
        return resList;
    }
}

package com.nanshen.common.utils.array;

import com.nanshen.common.utils.array.algorithm.SortStrategy;

/**
 * 类描述：数组工具类
 *
 * @author: nanshen
 * @date: 2019/9/16 19:47
 */
public class ArrayUtil {


    /**
     * 升序排序
     * @param array 待排序数组
     * @param sortStrategy 排序策略
     * @return
     */
    public Integer[] sortAsce(Integer[] array, SortStrategy sortStrategy){
        return sortStrategy.sortAsce(array);
    }


    /**
     * 降序排序
     * @param array 待排序数组
     * @param sortStrategy 排序策略
     * @return 降序后数组
     */
    public Integer[] sortDesc(Integer[] array, SortStrategy sortStrategy){
        return sortStrategy.sortDesc(array);
    }




}

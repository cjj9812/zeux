package com.nanshen.common.utils.array.algorithm;

/**
 * 类描述：冒泡排序算法实现类
 *
 * @author: nanshen
 * @date: 2019/9/16 19:51
 */
public class BubbleSortStrategy implements SortStrategy {
    /**
     * 升序排序
     * @param array 待排序数组
     * @return
     */
    @Override
    public Integer[] sortAsce(Integer[] array) {
        for (int i=0;i<array.length-1;i++){
            for(int j=0;j<=array.length-2-i;j++){
                if(array[j]>array[j+1]){
                    Integer tempValue=array[j+1];
                    array[j+1]=array[j];
                    array[j]=tempValue;
                }
            }
        }
        return array;
    }

    /**
     * 降序排序
     * @param array 待排序数组
     * @return
     */
    @Override
    public Integer[] sortDesc(Integer[] array) {
        return new Integer[0];
    }
}

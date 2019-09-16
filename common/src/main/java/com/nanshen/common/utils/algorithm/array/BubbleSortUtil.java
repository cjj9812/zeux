package com.nanshen.common.utils.algorithm.array;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：冒泡排序工具类
 *
 * @author: nanshen
 * @date: 2019/9/16 18:56
 */
public class BubbleSortUtil {


    /**
     * 冒泡排序
     * @param array 待排序数组
     * @return
     */
    public static Integer[] bubbleSort(Integer[] array){
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


    public static void main(String[] args) {
        Integer[] array={9,3,2,2,4,8};
        Integer[] resultArray=BubbleSortUtil.bubbleSort(array);
        for (Integer integer : resultArray) {
            System.out.println(integer);
        }
    }

}

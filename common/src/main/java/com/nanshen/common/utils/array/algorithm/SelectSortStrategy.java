package com.nanshen.common.utils.array.algorithm;

/**
 * 类描述：选择排序算法实现类
 *
 * @author: nanshen
 * @date: 2019/9/16 20:09
 */
public class SelectSortStrategy implements SortStrategy {
    /**
     * 升序排序
     * @param array 待排序数组
     * @return 排序后数组
     */
    @Override
    public Integer[] sortAsce(Integer[] array) {
        for (int i=0;i<array.length-1;i++){
            int min=i;
            for(int j=i+1;j<=array.length-1;j++){
                if(array[j]<array[min]){
                    min=j;
                }
            }
            int tempValue=array[i];
            array[i]=array[min];
            array[min]=tempValue;
        }
        return array;
    }

    /**
     * 降序排序
     * @param array 待排序数组
     * @return 排序后数组
     */
    @Override
    public Integer[] sortDesc(Integer[] array) {
        return new Integer[0];
    }


    public static void main(String[] args) {
        Integer[] array={19,9,1,1,8,4,5,4};
        SelectSortStrategy selectSortStrategy=new SelectSortStrategy();
        Integer[] result=selectSortStrategy.sortAsce(array);
        for (Integer integer : result) {
            System.out.println(integer);
        }
    }
}

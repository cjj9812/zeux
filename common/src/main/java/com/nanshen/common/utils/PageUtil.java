package com.nanshen.common.utils;




import com.nanshen.common.vo.PageVO;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {

    public static PageVO page(List list, Integer pageNum, Integer pageSize){
        PageVO pageVO=new PageVO();
        List resultList=new ArrayList();
        //总数
        int total=list.size();
        int pages=getPages(total,pageSize);

        if(list==null||list.size()==0)return null;

        if(pageSize>=total){
            pageVO.setTotal(total*1L);
            pageVO.setPages(pages);
            pageVO.setHasNextPage(false);
            pageVO.setNextPage(2);
            pageVO.setHasPreviousPage(false);
            pageVO.setPrePage(0);
            pageVO.setPageSize(pageSize);
            pageVO.setPageNum(pageNum);
            pageVO.setList(list);
            return pageVO;
        }
        if(pageNum==pages){
            resultList=list.subList(pageNum*pageSize-pageSize,total);
            pageVO.setTotal(total*1L);
            pageVO.setPages(pages);
            pageVO.setHasNextPage(false);
            pageVO.setNextPage(pageNum+1);
            pageVO.setHasPreviousPage(true);
            pageVO.setPrePage(pageNum-1);
            pageVO.setPageSize(pageSize);
            pageVO.setPageNum(pageNum);
            pageVO.setList(resultList);
            return pageVO;
        }
        resultList=list.subList(pageNum*pageSize-pageSize,pageNum*pageSize);
        pageVO.setPageNum(pageNum);
        pageVO.setPageSize(pageSize);
        pageVO.setPages(pages);
        pageVO.setTotal(list.size()*1L);
        if(pageNum-1<=0){
            pageVO.setPrePage(0);
            pageVO.setHasPreviousPage(false);
        }else{
            pageVO.setPrePage(pageNum-1);
        }
        if(pageNum*pageSize-total<=0){
            pageVO.setNextPage(pageNum+1);
            pageVO.setHasNextPage(false);
        }
        pageVO.setList(resultList);
        return pageVO;
    }


    /**
     * 获取总页数
     * @param total
     * @param pageSize
     * @return
     */
    private static Integer getPages(Integer total,Integer pageSize){
        int pages=1;
        int result=total/pageSize;
        int result1=total%pageSize;
        if(result<=0){
            pages=1;
        }else if(result>0){
            if(result1!=0){
                pages=result+1;
            }else{
                pages=result;
            }
        }
        return pages;
    }

}
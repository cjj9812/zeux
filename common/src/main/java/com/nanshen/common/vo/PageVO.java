package com.nanshen.common.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PageVO<T> implements Serializable {

    private Integer pageNum;
    private Integer pageSize;
    private Long total;
    private Integer pages;
    private Integer prePage;
    private Integer nextPage;
    private Boolean hasNextPage;
    private Boolean hasPreviousPage;
    private List<T> list;

    public PageVO(PageInfo pageInfo){
        this.list=pageInfo.getList();
        this.pageNum=pageInfo.getPageNum();
        this.pageSize=pageInfo.getPageSize();
        this.total=pageInfo.getTotal();
        this.prePage=pageInfo.getPrePage();
        this.nextPage=pageInfo.getNextPage();
        this.pages=pageInfo.getPages();
        this.hasPreviousPage=pageInfo.isHasPreviousPage();
        this.hasNextPage=pageInfo.isHasNextPage();
    }

    public PageVO(){}
}

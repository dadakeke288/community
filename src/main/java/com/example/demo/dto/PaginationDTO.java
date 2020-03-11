package com.example.demo.dto;

import com.example.demo.model.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showLastPage;

    private Integer curPage;
    private Integer lastPage;
    private List<Integer> pages;

    public void setPageArr(Integer totalCount, Integer page, Integer size) {
        Integer totalPages = (int)Math.ceil(totalCount/(double)size);
        if(page<1) page =1;
        if (page>totalPages) page = totalPages;
        this.showPrevious = page>1;
        this.showNext = page<totalPages;
        //分页展示出来的页面放进pages
        int sums = totalPages>5?5:totalPages;
        int beg,end;
        if(page<=totalPages-(int)sums/2){
            beg = page-(int)sums/2>0?page-(int)sums/2:1;
            end = beg+sums-1<totalPages?beg+sums-1:totalPages;
        }else {
            end = page+(int)sums/2<totalPages?page+(int)sums/2:totalPages;
            beg = end-sums+1>0?end-sums+1:1;
        }

        pages = new ArrayList<>();
        for(int i=beg;i<=end;i++){
            pages.add(i);
        }
        this.curPage = page;
        this.lastPage = totalPages;
        this.showLastPage = !pages.contains(totalPages);
        this.showFirstPage = !pages.contains(1);
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

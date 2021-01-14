package com.example.appoperation.db.base;

import java.util.List;

public class Page<T> {
    private final List<T> elements;

    private final int totalPage;

    private final int pageNum;

    private final int pageSize;

    private final long totalElementsNum;

    private Page(List<T> elements, int pageNum, int pageSize, int totalElementsNum) {
        this.elements = elements;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalElementsNum = totalElementsNum;

        if (totalElementsNum == 0)
            totalPage = 1;
        else
            totalPage = (pageSize + totalElementsNum - 1) / pageSize;
    }

    public static <T> Page<T> of(List<T> elements, Pageable pageable, int totalElementsNum) {
        return new Page<>(elements, pageable.getPageNum(), pageable.getPageSize(), totalElementsNum);
    }

    public List<T> getElements() {
        return elements;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElementsNum() {
        return totalElementsNum;
    }

    public int firstPageNum() {
        return 1;
    }

    public int lastPageNum() {
        return totalPage;
    }

    public int nextPageNum() {
        return pageNum + 1;
    }

    public int previousPageNum() {
        return pageNum - 1;
    }

    public boolean isFirstPage() {
        return pageNum == firstPageNum();
    }

    public boolean isLastPage() {
        return !hasNext();
    }

    public boolean hasPrevious() {
        return !isFirstPage();
    }

    public boolean hasNext() {
        return pageNum < totalPage;
    }
}

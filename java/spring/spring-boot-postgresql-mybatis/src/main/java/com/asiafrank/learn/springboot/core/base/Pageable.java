package com.asiafrank.learn.springboot.core.base;

/**
 * @author user created at 4/27/2017.
 */
public class Pageable {
    private final int pageNum;

    private final int pageSize;

    private Pageable(int pageNum, int pageSize) {
        if (pageNum <= 0) {
            throw new IllegalArgumentException("pageNum must >= 1");
        }

        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize must >= 1");
        }

        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public static Pageable of(int pageNum, int pageSize) {
        return new Pageable(pageNum, pageSize);
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getOffset() {
        return pageSize * (pageNum - 1);
    }

    @Override
    public String toString() {
        return "Pageable{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}

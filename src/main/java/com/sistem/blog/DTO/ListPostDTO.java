package com.sistem.blog.DTO;


import java.util.List;

public class ListPostDTO {
    private List<PostDTO> data;
    private int page;
    private int limit;
    private int allResult;
    private boolean lastPage;

    public List<PostDTO> getData() {
        return data;
    }

    public void setData(List<PostDTO> result) {
        this.data = result;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getAllResult() {
        return allResult;
    }

    public void setAllResult(int allResult) {
        this.allResult = allResult;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }
}

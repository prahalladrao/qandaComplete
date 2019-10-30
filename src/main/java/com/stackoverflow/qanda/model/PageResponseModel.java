package com.stackoverflow.qanda.model;

import org.springframework.data.domain.Page;

public class PageResponseModel {
    private long numberOfPosts;
    private Page<Post> page;

    public long getNumberOfPosts() {
        return numberOfPosts;
    }

    public PageResponseModel(long numberOfPosts, Page<Post> page) {
        this.numberOfPosts = numberOfPosts;
        this.page = page;
    }

    public void setNumberOfPosts(long numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

    public Page<Post> getPage() {
        return page;
    }

    public void setPage(Page<Post> page) {
        this.page = page;
    }

}

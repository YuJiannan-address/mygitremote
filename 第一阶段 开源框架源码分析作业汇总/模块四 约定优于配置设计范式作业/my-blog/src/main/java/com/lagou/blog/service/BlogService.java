package com.lagou.blog.service;

import com.github.pagehelper.PageInfo;
import com.lagou.blog.pojo.Article;

public interface BlogService {
    PageInfo<Article> queryArticlePage(Integer pageIdx, Integer pageSize);
}

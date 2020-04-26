package com.lagou.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.blog.dto.PagedList;
import com.lagou.blog.mapper.ArticleMapper;
import com.lagou.blog.pojo.Article;
import com.lagou.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private ArticleMapper articleMapper;

    public PageInfo<Article> queryArticlePage(Integer pageIdx, Integer pageSize) {
        PageHelper.startPage(pageIdx, pageSize);
        List<Article> articles = articleMapper.queryArticle();
        PageInfo<Article> page = new PageInfo<>(articles);
        return page;
    }
}

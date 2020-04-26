package com.lagou.blog.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.blog.pojo.Article;
import com.lagou.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/")
    public ModelAndView toIndex(Integer pageIdx, Integer pageSize) {
        if (pageIdx == null || pageIdx < 1) {
            pageIdx = 1;
        }
        if (pageSize == null || pageSize < 2) {
            pageSize = 2;
        }

        PageInfo<Article> pageInfo = blogService.queryArticlePage(pageIdx, pageSize);
        ModelAndView mav = new ModelAndView();
        mav.addObject("pageInfo", pageInfo);
        mav.addObject("articles", pageInfo.getList());
        mav.setViewName("client/index");
        System.out.println(pageInfo);
        return mav;
    }

}

package com.lagou.blog.mapper;

import com.lagou.blog.pojo.Article;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleMapper {

    @Select("select id,title,content,created,modified,categories,tags,allow_comment,thumbnail from t_article")
    List<Article> queryArticle();
}

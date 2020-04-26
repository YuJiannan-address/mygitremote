package com.lagou.blog.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Article {
    private Integer id;
    private String title;
    private String content;
    private Date created;
    private Date modified;
    private String categories;
    private Integer allowComment;
    private String thumbnail;
}

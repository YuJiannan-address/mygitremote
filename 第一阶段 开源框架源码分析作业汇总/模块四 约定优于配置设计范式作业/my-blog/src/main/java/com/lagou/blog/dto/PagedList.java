package com.lagou.blog.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class PagedList<T> {
    private List<T> datas;
    private Integer pageIdx;
    private Integer pageCount;
    private Long totalCount;
    private Integer pageSize;
}

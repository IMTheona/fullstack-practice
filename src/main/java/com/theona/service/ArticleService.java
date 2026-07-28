package com.theona.service;

import com.theona.pojo.Article;
import com.theona.pojo.PageBean;

public interface ArticleService {
    // 新增文章
    void add(Article article);

    // 条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    // 查找文章
    Article fineById(Integer id);

    // 删除文章
    void delete(Integer id);

    // 更新文章
    void update(Article article);
}

package com.theona.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.theona.mapper.ArticleMapper;
import com.theona.pojo.Article;
import com.theona.pojo.PageBean;
import com.theona.service.ArticleService;
import com.theona.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    // 新增文章
    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);

        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        PageBean<Article> pageBean = new PageBean<>();

        PageHelper.startPage(pageNum,pageSize);

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        List<Article> list = articleMapper.list(userId,categoryId,state);
        Page<Article> page = (Page<Article>) list;

        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());

        return pageBean;
    }
}

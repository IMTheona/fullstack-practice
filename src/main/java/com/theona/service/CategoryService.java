package com.theona.service;

import com.theona.pojo.Category;

import java.util.List;

public interface CategoryService {
    // 新增分类
    void add(Category category);

    // 文章列表
    List<Category> list();

    // 根据id查询分类详情
    Category findById(Integer id);

    // 更新分类
    void update(Category category);

    // 删除分类
    void delete(Integer id);
}

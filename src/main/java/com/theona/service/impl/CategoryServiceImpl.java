package com.theona.service.impl;

import com.theona.mapper.CategoryMapper;
import com.theona.pojo.Category;
import com.theona.pojo.User;
import com.theona.service.CategoryService;
import com.theona.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        // 补充数据
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateTime(LocalDateTime.now());

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        category.setCreateUser(userId);

        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId  =(Integer) map.get("id");

        return categoryMapper.list(userId);
    }

    @Override
    public Category findById(Integer id) {
        Category category = categoryMapper.findById(id);
        return category;
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }
}

package com.theona.pojo;

import com.theona.anno.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class Article {
    private Integer id; // 主键id
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String title;   // 文章标题
    @NotEmpty
    private String content; // 文章内容
    @NotEmpty
    @URL
    private String coverImg;// 文章封面
    @State
    private String state;   // 文章状态
    @NotNull
    private Integer categoryId; // 文章分类
    private Integer createUser; // 发布者
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

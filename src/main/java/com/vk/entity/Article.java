package com.vastknowledge.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_article")
public class Article extends BaseEntity {

    private Long userId;

    private String title;

    private String content;

    private String summary;

    private String cover;

    private Integer viewCount;

    private Integer likeCount;

    private Integer commentCount;

    private Integer status;
}

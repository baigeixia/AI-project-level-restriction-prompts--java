package com.vastknowledge.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_comment")
public class Comment extends BaseEntity {

    private Long articleId;

    private Long userId;

    private Long parentId;

    private String content;

    private Integer likeCount;
}

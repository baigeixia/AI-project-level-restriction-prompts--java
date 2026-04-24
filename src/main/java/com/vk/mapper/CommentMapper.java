package com.vastknowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vastknowledge.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper&lt;Comment&gt; {
}

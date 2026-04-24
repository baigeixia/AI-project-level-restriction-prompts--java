package com.vastknowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vastknowledge.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper&lt;Article&gt; {
}

package com.vastknowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vastknowledge.entity.Article;
import com.vastknowledge.mapper.ArticleMapper;
import com.vastknowledge.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl&lt;ArticleMapper, Article&gt; implements ArticleService {
}

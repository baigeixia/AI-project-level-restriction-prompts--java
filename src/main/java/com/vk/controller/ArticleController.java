package com.vastknowledge.controller;

import com.vastknowledge.common.Result;
import com.vastknowledge.entity.Article;
import com.vastknowledge.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/{id}")
    public Result&lt;Article&gt; getById(@PathVariable Long id) {
        Article article = articleService.getById(id);
        return Result.success(article);
    }
}

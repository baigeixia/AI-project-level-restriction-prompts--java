package com.vastknowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vastknowledge.entity.Comment;
import com.vastknowledge.mapper.CommentMapper;
import com.vastknowledge.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl&lt;CommentMapper, Comment&gt; implements CommentService {
}

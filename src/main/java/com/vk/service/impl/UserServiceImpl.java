package com.vastknowledge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vastknowledge.entity.User;
import com.vastknowledge.mapper.UserMapper;
import com.vastknowledge.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl&lt;UserMapper, User&gt; implements UserService {
}

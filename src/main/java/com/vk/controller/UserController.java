package com.vastknowledge.controller;

import com.vastknowledge.common.Result;
import com.vastknowledge.entity.User;
import com.vastknowledge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public Result&lt;User&gt; getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return Result.success(user);
    }
}

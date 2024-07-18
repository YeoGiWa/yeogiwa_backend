package com.example.yeogiwa.controller;

import com.example.yeogiwa.dto.RegisterDTO;
import com.example.yeogiwa.entity.UserEntity;
import com.example.yeogiwa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public UserEntity getUserController(@RequestParam int id) {
        return userService.getUser(id);
    }

    @PostMapping("/register")
    public int register(@RequestBody RegisterDTO registerDTO) {
        return userService.createUser(registerDTO);
    }
}

package com.example.cricketmanagement.controller;

import com.example.cricketmanagement.dto.UserDTO;
import com.example.cricketmanagement.model.Users;
import com.example.cricketmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping()
    public UserDTO createUser(@RequestBody Users user){
        return userService.createUser(user);
    }

}

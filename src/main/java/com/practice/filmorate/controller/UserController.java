package com.practice.filmorate.controller;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.exception.ValidateException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSInput;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.update(user);
    }


    @GetMapping
    public List<User> allUsers() {
        return userService.findAll();
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable int userId,@PathVariable int friendId){
        userService.addFriend(userId,friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteDriend(@PathVariable int userId,@PathVariable int friendId){
        userService.deleteFriend(userId,friendId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> findAllFriend(@PathVariable int userId){
        return userService.findAllFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{friendId}")
    public List<User> findAllCommonFriend(@PathVariable int userId, @PathVariable int friendId){
        return userService.findAllCommonFriends(userId,friendId);
    }
}
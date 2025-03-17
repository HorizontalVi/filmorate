package com.practice.filmorate.controller;

import com.practice.filmorate.exception.FilmNotFoundException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    HashMap<String, User> users = new HashMap<>();

    @PostMapping
    public User addUser(@RequestBody User user){
        users.put(user.getEmail(), user);
        return user;
    }

    @PutMapping
    public User updateFilm(@RequestBody User user){
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new RuntimeException("Почта не может быть пустой");
        }
        users.put(user.getEmail(), user);
        return user;
    }

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }
}

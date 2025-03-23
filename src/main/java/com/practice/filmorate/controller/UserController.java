package com.practice.filmorate.controller;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.exception.ValidateException;
import com.practice.filmorate.model.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    List<User> users = new ArrayList<>();
    private int userCounter = 1;

    @PostMapping
    public User addUser(@RequestBody User user) {
        validate(user);
        users.add(user);
        user.setId(userCounter++);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        validate(user);
        for (User existingUser : users) {
            if (existingUser.getId() == user.getId()) {
                existingUser.setName(user.getName());
                existingUser.setLogin(user.getLogin());
                existingUser.setEmail(user.getLogin());
                existingUser.setBirthday(user.getBirthday());
                return user;
            }
        }
        throw new NotFoundException("Пользователь не найден");
    }

    private void validate(User user) {
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new ValidateException("Почта пользователя должна содержать @");
        }
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            throw new ValidateException("Логин не может быть пустым");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidateException("Дата рождения не может быть в будущем");
        }
    }

    @GetMapping
    public List<User> allUsers() {
        return users;
    }
}
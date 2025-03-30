package com.practice.filmorate.storage;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.exception.ValidateException;
import com.practice.filmorate.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage{
    List<User> users = new ArrayList<>();
    private int userCounter = 1;

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findById(int id) {
        for (User user : users){
            if (user.getId() == id){
                return user;
            }
        };
        throw new NotFoundException("Пользователь не найден");
    }

    @Override
    public User create(User user) {
        user.setId(userCounter++);
        users.add(user);
        return user;
    }

    @Override
    public User update(User user) {
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
}
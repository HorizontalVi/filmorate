package com.practice.filmorate.storage;

import com.practice.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    List<User> findAll(); // получение списка пользователей

    User findById(int id); // получение пользователя по идентификатору

    User create(User user); // создание пользователя

    User update(User user); // обновление пользователя
}

package com.practice.filmorate.storage;

import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.User;
import org.springframework.stereotype.Component;

import java.util.List;


public interface FilmStorage {
    List<Film> findAll(); // получение списка пользователей

    Film findById(int id); // получение пользователя по идентификатору

    Film create(Film film); // создание пользователя

    Film update(Film film); // обновление пользователя
}

package com.practice.filmorate.service;

import com.practice.filmorate.exception.ValidateException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.FilmStorage;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Comparator;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film findById(int id) {
        return filmStorage.findById(id);
    }

    public Film create(Film film) {
        validate(film);
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        validate(film);
        return filmStorage.update(film);
    }


    public void addLike(int userId,int filmId){
        Film film = filmStorage.findById(filmId);
        userStorage.findById(userId);
        film.getLikes().add(userId);
    }

    public void removeLike (int userId, int filmId){
        Film film = filmStorage.findById(filmId);
        userStorage.findById(userId);
        film.getLikes().remove(userId);

    }

    public List<Film> findAllPopularFilms(int count){
        Comparator<Film> comporator = (Film o1, Film o2) -> o2.getLikes().size() - o1.getLikes().size();
        return filmStorage.findAll().stream()
                .sorted(comporator)
                .limit(count)
                .toList();
    }

    private void validate(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidateException("Название фильма не может быть пустым");
        }
        if (film.getDescription() == null || film.getDescription().length() >= 200) {
            throw new ValidateException("Описание не может быть больше 200 символов");
        }
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidateException("Дата релиза фильма не может быть раньше 28 декабря 1895 года");
        }
        if (film.getDuration() <= 0) {
            throw new ValidateException("Продолжительность фильма должна быть положительной");
        }
    }
}
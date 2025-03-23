package com.practice.filmorate.controller;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.exception.ValidateException;
import com.practice.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final List<Film> films = new ArrayList<>();
    private int counter = 1;

    @PostMapping
    public Film addFilm(@RequestBody Film film){
        validate(film);
        films.add(film);
        film.setId(counter++);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film){
        validate(film);
        for (Film item : films){
            if (item.getId() == film.getId()){
                item.setName(film.getName());
                item.setDuration(film.getDuration());
                item.setDescription(film.getDescription());
                item.setReleaseDate(film.getReleaseDate());
                return item;
            }
        }
        throw new NotFoundException("Фильм не найден");
    }

    private void validate(Film film) {
        if (film.getName() == null || film.getName().isBlank()){
            throw new ValidateException("Название фильма не может быть пустым");
        }
        if (film.getDescription() == null || film.getDescription().length() >= 200){
            throw new ValidateException("Описание не может быть больше 200 символов");
        }
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            throw new ValidateException("Дата релиза фильма не может быть раньше 28 декабря 1895 года");
        }
        if(film.getDuration() <= 0){
            throw new ValidateException("Продолжительность фильма должна быть положительной");
        }
    }

    @GetMapping
    public List <Film> allFilms(){
        return films;
    }
}
package com.practice.filmorate.controller;

import com.practice.filmorate.exception.FilmNotFoundException;
import com.practice.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private List<Film> films = new ArrayList<>();

    @PostMapping
    public Film addFilm(@RequestBody Film film){
        films.add(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film){
        for (Film item : films){
            if (item.getId() == film.getId()){
                item.setName(film.getName());
                item.setDuration(film.getDuration());
                return item;
            }
        }
        throw new FilmNotFoundException("Фильм не найден");
    }

    @GetMapping
    public List <Film> allFilms(){
        return films;
    }
}
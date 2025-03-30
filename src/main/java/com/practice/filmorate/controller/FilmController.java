package com.practice.filmorate.controller;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.exception.ValidateException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public Film addFilm(@RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        return filmService.update(film);
    }

    @GetMapping
    public List<Film> allFilms() {
        return filmService.findAll();
    }


}
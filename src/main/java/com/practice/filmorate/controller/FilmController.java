package com.practice.filmorate.controller;

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

    // get //films/

    @GetMapping("/popular")
    public List<Film> findPopularFilms(@RequestParam int count){
        return filmService.findAllPopularFilms(count);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addLike(@PathVariable int filmId, @PathVariable int userId){
        filmService.addLike(userId,filmId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void removeLike(@PathVariable int filmId, @PathVariable int userId){
        filmService.removeLike(userId,filmId);
    }
}
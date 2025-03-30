package com.practice.filmorate.storage;

import com.practice.filmorate.exception.NotFoundException;
import com.practice.filmorate.model.Film;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final List<Film> films = new ArrayList<>();
    private int counter = 1;

    @Override
    public List<Film> findAll() {
        return films;
    }

    @Override
    public Film findById(int id) {
        for (Film film : films) {
            if (film.getId() == id) {
                return film;
            }
        }
        throw new NotFoundException("Фильм не найден");
    }

    @Override
    public Film create(Film film) {
        film.setId(counter++);
        films.add(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        for (Film item : films) {
            if (item.getId() == film.getId()) {
                item.setName(film.getName());
                item.setDuration(film.getDuration());
                item.setDescription(film.getDescription());
                item.setReleaseDate(film.getReleaseDate());
                return item;
            }
        }
        throw new NotFoundException("Фильм не найден");
    }
}

package com.practice.filmorate.controller;

import com.practice.filmorate.exception.ValidateException;
import com.practice.filmorate.model.Film;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

public class FilmControllerTest {
    private FilmController filmController;

    @BeforeEach
    void setUp(){
        filmController = new FilmController();
    }

    @Test
    void addFilm_shouldThrowException_whenNullNameGiven(){
        Film film = new Film();
        film.setDescription("Description Test");
        film.setDuration(23);
        film.setReleaseDate(LocalDate.of(1922,11,10));

        ValidateException validateException = Assertions.assertThrows(ValidateException.class, () -> filmController.addFilm(film));
        Assertions.assertEquals("Название фильма не может быть пустым",validateException.getMessage());
    }

    @Test
    void shouldFail_WhenDescriptionExceeds200Characters(){
        Film film = new Film();
        film.setName("Batman");
        film.setDescription("The Batman is a 2022 American superhero film based on the DC Comics character Batman. Directed by Matt Reeves from a screenplay he wrote with Peter Craig, it is a reboot of the Batman film franchise produced by DC Films. Robert Pattinson stars as Bruce Wayne / Batman alongside Zoë Kravitz, Paul Dano, Jeffrey Wright, John Turturro, Peter Sarsgaard, Andy Serkis, and Colin Farrell. The film sees Batman, in his second year fighting crime in Gotham City, uncover corruption with ties to his own family while pursuing the Riddler (Dano), a mysterious serial killer targeting the city's elite.");
        film.setDuration(130);
        film.setReleaseDate(LocalDate.of(1997,10,20));
        ValidateException validateException = Assertions.assertThrows(ValidateException.class,() -> filmController.addFilm(film));
        Assertions.assertEquals("Описание не может быть больше 200 символов",validateException.getMessage());
    }

    @Test
    void shouldFail_WhenReleaseDateBefore1895December28(){
        Film film = new Film();
        film.setName("Interstellar");
        film.setReleaseDate(LocalDate.of(1880,10,20));
        film.setDescription("Description");
        film.setDuration(120);

        ValidateException validateException = Assertions.assertThrows(ValidateException.class,() -> filmController.addFilm(film));
        Assertions.assertEquals("Дата релиза фильма не может быть раньше 28 декабря 1895 года",validateException.getMessage());
    }

    @Test
    void shouldFail_WhenDurationIsZeroOrNegative(){
        Film film = new Film();
        film.setName("Interstellar");
        film.setReleaseDate(LocalDate.of(1990,10,20));
        film.setDescription("Description");
        film.setDuration(-120);

        ValidateException validateException = Assertions.assertThrows(ValidateException.class,() -> filmController.addFilm(film));
        Assertions.assertEquals("Продолжительность фильма должна быть положительной",validateException.getMessage());
    }

    @Test
    void successAddFilmScenario(){
        Film film = new Film();
        film.setName("Batman");
        film.setDescription("The Batman is a 2022 American superhero film based on the DC Comics character Batman.");
        film.setDuration(130);
        film.setReleaseDate(LocalDate.of(1997,10,20));

        Film savedFilm =  filmController.addFilm(film);

        Assertions.assertEquals(film.getName(),savedFilm.getName());
        Assertions.assertEquals(film.getDescription(),savedFilm.getDescription());
        Assertions.assertEquals(film.getDuration(),savedFilm.getDuration());
        Assertions.assertEquals(film.getReleaseDate(),savedFilm.getReleaseDate());
    }
}
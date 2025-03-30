package com.practice.filmorate.controller;

import com.practice.filmorate.exception.ValidateException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.service.UserService;
import com.practice.filmorate.storage.InMemoryUserStorage;
import com.practice.filmorate.storage.UserStorage;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


public class UserControllerTest {
    private UserController userController;

    @BeforeEach
    void setUP() {
        UserStorage userStorage = new InMemoryUserStorage();
        UserService userService = new UserService(userStorage);
        userController = new UserController(userService);
    }

    @Test
    void shouldThrowException_WhenEmailIsEmpty() {
        User user = new User();
        user.setName("Thomas");
        user.setLogin("thomas_andre");
        user.setBirthday(LocalDate.of(1975, 6, 7));
        user.setEmail("Th.andre.com");

        ValidateException validateException = Assertions.assertThrows(ValidateException.class, () -> userController.addUser(user));
        Assertions.assertEquals("Почта пользователя должна содержать @", validateException.getMessage());
    }

    @Test
    void shouldThrowException_WhenLoginIsEmpty() {
        User user = new User();
        user.setName("Thomas");
        user.setBirthday(LocalDate.of(1975, 6, 7));
        user.setEmail("Th.andre@apple.com");

        ValidateException validateException = Assertions.assertThrows(ValidateException.class, () -> userController.addUser(user));
        Assertions.assertEquals("Логин не может быть пустым", validateException.getMessage());
    }

    @Test
    void shouldUseLoginAsDisplayName_WhenNameIsEmpty() {
        User user = new User();
        user.setLogin("thomas_andre");
        user.setName(" ");
        user.setBirthday(LocalDate.of(1975, 6, 7));
        user.setEmail("Th.andre@apple.com");

        userController.addUser(user);
        Assertions.assertEquals("thomas_andre", user.getName());
    }

    @Test
    void shouldThrowException_WhenBirthDateIsInTheFuture() {
        User user = new User();
        user.setName("Thomas");
        user.setLogin("th.andre");
        user.setBirthday(LocalDate.of(2100, 6, 7));
        user.setEmail("Th.andre@apple.com");

        ValidateException validateException = Assertions.assertThrows(ValidateException.class, () -> userController.addUser(user));
        Assertions.assertEquals("Дата рождения не может быть в будущем", validateException.getMessage());
    }

    @Test
    void succesAddUserScenario() {
        User user = new User();
        user.setLogin("thomas_andre");
        user.setName("Thomas Creed");
        user.setBirthday(LocalDate.of(1975, 6, 7));
        user.setEmail("Th.andre@apple.com");

        User savedUser = userController.addUser(user);
        Assertions.assertEquals(user.getName(), savedUser.getName());
        Assertions.assertEquals(user.getLogin(), savedUser.getLogin());
        Assertions.assertEquals(user.getEmail(), savedUser.getEmail());
        Assertions.assertEquals(user.getBirthday(), savedUser.getBirthday());
    }
}

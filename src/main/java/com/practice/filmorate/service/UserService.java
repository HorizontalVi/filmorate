package com.practice.filmorate.service;

import com.practice.filmorate.exception.ValidateException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;


    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User findById(int id) {
        return userStorage.findById(id);
    }

    public User create(User user) {
        validate(user);
        return userStorage.create(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public void addFriend(int userId, int friendId) {
        User user = userStorage.findById(userId);
        User friend = userStorage.findById(friendId);
        user.getFriends().add(friend);
        friend.getFriends().add(user);
    }

    public void deleteFriend(int userId, int friendId) {
        User user = userStorage.findById(userId);
        User friend = userStorage.findById(friendId);
        user.getFriends().remove(friend);
        user.getFriends().remove(user);
    }

    public Set<User> findAllFriends(int userId) {
        return userStorage.findById(userId).getFriends();
    }

    public Set<User> findAllCommonFriends(int userId, int friendId) {
        User user = userStorage.findById(userId);
        User friend = userStorage.findById(friendId);
        Set<User> commonFriends = new HashSet<>();
        for (User user1 : user.getFriends()){
            if (friend.getFriends().contains(user1)){
                commonFriends.add(user1);
            }
        }
        return commonFriends;
    }

    private void validate(User user) {
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new ValidateException("Почта пользователя должна содержать @");
        }
        if (user.getLogin() == null || user.getLogin().isBlank()) {
            throw new ValidateException("Логин не может быть пустым");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidateException("Дата рождения не может быть в будущем");
        }
    }
}

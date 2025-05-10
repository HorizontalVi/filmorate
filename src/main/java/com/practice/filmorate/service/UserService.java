package com.practice.filmorate.service;

import com.practice.filmorate.exception.ValidateException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void deleteFriend(int userId, int friendId) {
        User user = userStorage.findById(userId);
        User friend = userStorage.findById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> findAllFriends(int userId) {
        User user = userStorage.findById(userId);
        Set<Integer> friendIds = user.getFriends();
        List<User> result = new ArrayList<>();
        for (Integer friendId : friendIds){
           User userFriend = findById(friendId);
           result.add(userFriend);
        }
        return result;

//        return user.getFriends().stream()
//                .map(friendId -> findById(friendId))
//                .toList();
    }

    public List<User> findAllCommonFriends(int userId, int friendId) {
        User user = userStorage.findById(userId);
        User friend = userStorage.findById(friendId);
        List<User> commonFriends = new ArrayList<>();
        for (Integer user1FriendId : user.getFriends()){
            if (friend.getFriends().contains(user1FriendId)){
                commonFriends.add(findById(user1FriendId));
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

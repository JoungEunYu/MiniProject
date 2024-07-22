package com.kh.mini.game.game.controller;

import java.util.List;

import com.kh.mini.game.dao.UserDAO;
import com.kh.mini.game.model.vo.User;

public class UserController {
    private UserDAO userDAO;
    private User currentUser;

    public UserController() {
        userDAO = new UserDAO();
    }

    public boolean register(String id) {
        if (!userDAO.userExists(id)) {
            userDAO.saveUser(new User(id, 0));
            return true;
        }
        return false;
    }

    public User getUser(String id) {
        return userDAO.getUser(id);
    }

    public void incrementScore() {
        if (currentUser != null) {
            User user = userDAO.getUser(currentUser.getId());
            if (user != null) {
                user.setScore(user.getScore() + 1);
                userDAO.saveUser(user);
                // Update currentUser with the latest score
                currentUser = user;
            }
        }
    }

    public void saveScore() {
        if (currentUser != null) {
            userDAO.saveUser(currentUser);
        }
    }

    public void logout() {
        // 로그아웃 시 사용자 정보 저장 처리
        saveScore();
    }

    public List<User> getLeaderboard() {
        return userDAO.getLeaderboard();
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}

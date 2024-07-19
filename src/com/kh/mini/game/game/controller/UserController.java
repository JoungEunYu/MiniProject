package com.kh.mini.game.game.controller;

import com.kh.mini.game.dao.UserDAO;
import com.kh.mini.game.model.vo.User;

public class UserController {
	private UserDAO userDAO;
	private User loggedInUser;
	
	public UserController()  {
		userDAO = new UserDAO();
	}
	
	public boolean login(String id) {
		User user = userDAO.getUser(id);
		if(user == null) {
			System.out.println("등록되지 않은 계정입니다. 회원가입을 해주세요");
			return false;
		}
		loggedInUser = user;
		return true;
	}
	
	public boolean register(String id) {
		if(userDAO.userExists(id)) {
			System.out.println("중복된 아이디입니다. 다른 아이디를 입력해주세요");
			return false;
		}
		User user = new User(id, 0);
		userDAO.saveUser(user);
		userDAO.addUserId(id);
		loggedInUser = user;
		return true;
	}
	
	public void incrementScore() {
		if(loggedInUser != null) {
			loggedInUser.setScore(loggedInUser.getScore() +1);
		}
	}
	
	public int getScore() {
		return loggedInUser != null ? loggedInUser.getScore() : 0;
	}
	
	public void saveScore() {
		if(loggedInUser != null) {
			userDAO.saveUser(loggedInUser);
		}
	}
	
	public void logout() {
		saveScore();
		loggedInUser = null;
	}
}

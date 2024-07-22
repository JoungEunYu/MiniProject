package com.kh.mini.game.dao;

import com.kh.mini.game.model.vo.User;

import java.io.*;
import java.util.*;

public class UserDAO {
    private static final String FILE_PATH = "users.txt"; // 사용자 데이터 파일 경로

    public UserDAO() {
        // 파일이 존재하지 않는 경우 생성
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("파일 생성 오류: " + e.getMessage());
            }
        }
    }

    public boolean userExists(String id) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void saveUser(User user) {
        List<User> users = loadUsers();
        boolean updated = false;

        // 사용자 리스트에서 기존 사용자 정보를 업데이트
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                updated = true;
                break;
            }
        }

        // 사용자가 새로 추가된 경우
        if (!updated) {
            users.add(user);
        }

        writeUsers(users);
    }

    public User getUser(String id) {
        List<User> users = loadUsers();
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getLeaderboard() {
        List<User> users = loadUsers();
        users.sort((u1, u2) -> Integer.compare(u2.getScore(), u1.getScore()));
        return users;
    }

    private List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String id = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());
                    users.add(new User(id, score));
                }
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 오류: " + e.getMessage());
        }
        return users;
    }

    private void writeUsers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.write(user.getId() + "," + user.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("파일 쓰기 오류: " + e.getMessage());
        }
    }
}

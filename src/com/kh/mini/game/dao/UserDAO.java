package com.kh.mini.game.dao;
// init test 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import com.kh.mini.game.model.vo.User;

public class UserDAO {
	private static final String USER_IDS_FILE = "userIds.txt";
	
	public User getUser(String id) {
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(id + ".txt"))) {
			return (User) ois.readObject();
		} catch(FileNotFoundException e) {
			return null;
		} catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void saveUser(User user) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(user.getId() + ".txt"))) {
			oos.writeObject(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean userExists(String id) {
		return getUserIds().contains(id);
	}
	
	public void addUserId(String id) {
		Set<String> userIds = getUserIds();
		userIds.add(id);
		saveUserIds(userIds);
	}
	
	private Set<String> getUserIds() {
		Set<String> userIds = new HashSet<>();
		try (BufferedReader br = new BufferedReader(new FileReader(USER_IDS_FILE))) {
			String line;
			while((line = br.readLine()) != null) {
				userIds.add(line.trim());
			}
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userIds;
	}
	
	private void saveUserIds(Set<String> userIds) {
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(USER_IDS_FILE))) {
			for(String id : userIds) {
				bw.write(id);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

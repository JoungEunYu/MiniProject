package com.kh.mini.game.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.kh.mini.game.game.controller.UserController;
import com.kh.mini.game.model.vo.User;

public class GameView {
	private UserController userController;
	private User currentUser;

	public GameView() {
		userController = new UserController();
		currentUser = null;
	}

	public void start() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean running = true;

		while (running) {
			System.out.println("1. 로그인");
			System.out.println("2. 회원가입");
			System.out.println("3. 순위 확인");
			System.out.println("4. 종료");
			System.out.print("메뉴를 선택하세요 : ");

			try {
				int menu = Integer.parseInt(reader.readLine());
				switch (menu) {
				case 1:
					login(reader);
					break;
				case 2:
					register(reader);
					break;
				case 3:
					showLeaderboard();
					break;
				case 4:
					if (currentUser != null) {
						userController.saveScore(); // 점수를 저장
					}
					System.out.println("프로그램을 종료합니다.");
					running = false;
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
				}
			} catch (NumberFormatException | IOException e) {
				System.out.println("입력 오류: " + e.getMessage());
			}
		}
	}

	private void login(BufferedReader reader) throws IOException {
		System.out.print("아이디 입력 : ");
		String id = reader.readLine();
		currentUser = userController.getUser(id);

		if (currentUser != null) {
			System.out.println("로그인 성공! 당신의 점수는 : " + currentUser.getScore());
			userController.setCurrentUser(currentUser); // Set current user in UserController
			gameLoop(reader);
		} else {
			System.out.println("아이디가 존재하지 않습니다.");
		}
	}

	private void register(BufferedReader reader) throws IOException {
		System.out.print("원하시는 아이디를 입력해주세요 : ");
		String id = reader.readLine();

		if (userController.register(id)) {
			currentUser = userController.getUser(id);
			System.out.println("회원가입에 성공했습니다. 로그인 해주세요");
		}
	}

	private void gameLoop(BufferedReader reader) throws IOException {
		boolean inGame = true;
		while (inGame) {
			System.out.println("당신의 점수는 : " + currentUser.getScore());
			System.out.print("엔터를 누르면 점수가 오릅니다. 종료를 원하시면 'exit'을 입력해주세요\n");

			String input = reader.readLine();
			if ("exit".equalsIgnoreCase(input)) {
				inGame = false;
			} else {
				userController.incrementScore(); // 점수 증가
				// 점수 업데이트 후 현재 사용자 정보 갱신
				currentUser = userController.getUser(currentUser.getId());
			}
		}
		userController.saveScore(); // 점수 저장
		userController.logout(); // 로그아웃 시점에서 리더보드 업데이트
	}

	private void showLeaderboard() {
		List<User> leaderboard = userController.getLeaderboard();
		System.out.println("순위 목록:");
		for (int i = 0; i < leaderboard.size(); i++) {
			User user = leaderboard.get(i);
			System.out.println((i + 1) + "등: " + user.getId() + " - 점수: " + user.getScore());
		}
	}
}

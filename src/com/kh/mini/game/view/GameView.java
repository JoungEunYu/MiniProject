package com.kh.mini.game.view;

import java.util.Scanner;

import com.kh.mini.game.game.controller.UserController;

public class GameView {
	private Scanner scanner;
	private UserController userController;
	
	public GameView() {
		scanner = new Scanner(System.in);
		userController = new UserController();
	}
	
	public void start() {
		while(true) {
			System.out.println("1. 로그인");
			System.out.println("2. 회원가입");
			System.out.println("3. 종료");
			System.out.print("메뉴를 선택하세요 : ");
			String menu = scanner.nextLine();
			
			if(menu.equals("1")) {
				login();
			} else if (menu.equals("2")) {
				register();
			} else if (menu.equals("3")) {
				System.out.println("프로그램을 종료합니다.");
				break;
			} else {
				System.out.println("잘못된 입력입니다. 다시 시도해주세요");
			}
		}
	}
	
	private void login() {
		System.out.print("아이디 입력 : ");
		String userId = scanner.nextLine();
		if(userController.login(userId)) {
			gameLoop();
		}
	}
	
	private void register() {
		System.out.println("원하시는 아이디를 입력해주세요 : ");
		String userId = scanner.nextLine();
		if(userController.register(userId)) {
			System.out.println("회원가입에 성공 했습니다. 로그인 해주세요");
			gameLoop();
		}
	}
	
	private void gameLoop() {
		while(true) {
			System.out.println("당신의 점수는 : " + userController.getScore());
			System.out.println("엔터를 누르면 점수가 오릅니다. 종료를 원하시면 'exit'을 입력해주세요");
			String input = scanner.nextLine();
			
			if (input.equalsIgnoreCase("exit")) {
				userController.logout();
				System.out.println("프로그램을 종료합니다. 당신의 점수는 저장됩니다.");
				break;
			} else if (input.isEmpty()) {
				userController.incrementScore();
			} else {
				System.out.println("잘못된 입력입니다. 엔터키를 눌러야 점수가 오릅니다.");
			}
		}
	}
}

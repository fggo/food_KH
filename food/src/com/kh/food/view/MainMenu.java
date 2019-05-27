package com.kh.food.view;

import static com.kh.food.view.Constants.LOGOFF;
import static com.kh.food.view.Constants.MENU_EXIT;
import static com.kh.food.view.Constants.OFF;
import static com.kh.food.view.Constants.ORDER;
import static com.kh.food.view.Constants.SHOW_USERS;
import static com.kh.food.view.Constants.SIGNIN;
import static com.kh.food.view.Constants.SIGNUP;
import static com.kh.food.view.Constants.VIEW_ORDER;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import com.kh.food.controller.UserController;
import com.kh.food.model.vo.Food;
import com.kh.food.model.vo.MenuChoiceException;
import com.kh.food.model.vo.User;

public class MainMenu {
	public final static Scanner CONSOLE = new Scanner(System.in);
	private UserController controller;
//	private LoginPageFrame winFrame = new LoginPageFrame("food");
	
	public void showMainMenu() {
		System.out.println("=== 홈페이지 ===");
		System.out.println("1. 회원 가입");
		System.out.println("2. 로그인");
		System.out.println("3. 로그오프");
		System.out.println("4. 주문 하기");
		System.out.println("5. 주문 조회");
		System.out.println("6. 손님현황 조회");
		System.out.println("0. 프로그램 종료.");
		System.out.print("메뉴 입력: ");
	}

	public void initPageView() {
		
	}

	public void mainMenu(UserController controller) {
		this.controller = controller;
		int choice = -1;
		do {
			try {
				showMainMenu();
				choice = CONSOLE.nextInt(); CONSOLE.nextLine();
				if(choice <MENU_EXIT || choice > SHOW_USERS)
					throw new MenuChoiceException(choice);

				switch(choice) {
					case SIGNUP: controller.signUp(); break;
					case SIGNIN: controller.signIn(); break;
					case LOGOFF: controller.logOff(); break;
					case ORDER: controller.order(); break;
					case VIEW_ORDER: controller.viewOrder(); break;
					case SHOW_USERS: controller.showUsers(); seatView(); break;
					case MENU_EXIT:
						controller.logOff();
						controller.storeToFile();
						System.out.println("프로그램을 종료합니다.");
						return;
				}
			} catch(MenuChoiceException e) {
				e.showWrongChoice();
				System.out.println("메뉴선택을 다시 합니다.\n");
			}
		} while(choice != 0);
	}
	
	public User signUpView() {
		System.out.println("=== 회원 가입 ===");
		System.out.print("회원 이름 : ");
		String username = CONSOLE.nextLine();
		System.out.print("회원 전화 : ");
		String phone = CONSOLE.nextLine();
		System.out.print("회원 이메일 : " );
		String email = CONSOLE.nextLine();
		System.out.print("회원 주소 : ");
		String address = CONSOLE.nextLine();
		
		User user = new User(username, phone, email, address, OFF,
				new TreeMap<Food, Integer>(), null, -1);
		return user;
	}

	public String signInView() {
		System.out.println("=== 회원 로그인 ===");
		System.out.print("핸드폰번호 : ");
		String phone = CONSOLE.nextLine();

		return phone;
	}

	public void showFoodMenu() {
		System.out.println("=== 음식 메뉴 ===");
		System.out.println("  1. 햄버거 --- 2,000");
		System.out.println("  2. 치킨 --- 9,000");
		System.out.println("=== 음료 메뉴 ===");
		System.out.println("  3. 콜라 --- 1,000");
		System.out.println("  4. 우유 --- 500");
		System.out.println("  0. 주문 종료");
		System.out.print("메뉴번호 입력 : ");
	}

	public Map<Food,Integer> orderView(){
		Map<Food, Integer> orderList = new TreeMap<Food,Integer>();
		List<Food> foodMenu = controller.getFoodMenu();
		int choice = -1;
		int qty = 0;
		do {
			try {
				this.showFoodMenu();
				choice = CONSOLE.nextInt(); CONSOLE.nextLine();

				if(choice > MENU_EXIT && choice <= foodMenu.size()) {
					System.out.print("수량 : ");
					qty = CONSOLE.nextInt(); CONSOLE.nextLine();
					if(qty <0)
						throw new MenuChoiceException(qty);

					orderList.put(foodMenu.get(choice-1), qty);
				}
				else if(choice == MENU_EXIT) {
					System.out.println("주문을 마칩니다.");
					return orderList;
				}
				else {
					throw new MenuChoiceException(choice);
				}

//				switch(choice) {
//					case MENU_BURGER:
//						orderList.put(BURGER, qty);
//						total += PRICE_BURGER * qty;
//						break;
//					case MENU_EXIT:
//						System.out.println("주문을 완료하였습니다.");
//						return orderList;
//					default:
//						throw new MenuChoiceException(choice);
//				}
			} catch(MenuChoiceException e) {
				e.showWrongChoice();
				System.out.println("메뉴선택을 다시합니다.\n");
			}
		}while(choice!= 0);
		
		return orderList;
	}
	
	public void seatView() {
		System.out.println("좌석 현황(X: 빈좌석)");
		boolean[] reservations = controller.getReservations();
		
		for(int i=0;i<reservations.length; i++) {
			if(i==reservations.length/2)
				System.out.println();
			System.out.print((reservations[i]==true ? 'O':'X') + " ");
		}
		System.out.println();
	}

	public int reserveSeatView() {
		seatView();
		boolean[] reservations = controller.getReservations();
		Set<Integer> seatNos = new HashSet<Integer>();
		
		for(int i=0;i<reservations.length; i++)
			if(reservations[i]==false) seatNos.add(i+1);

		char answer = '\u0000';
		int seatNo = -1;
		do {
			System.out.print("식사하고 가시겠습니까? (Y/N) : ");
			answer = Character.toUpperCase(CONSOLE.nextLine().charAt(0));
			if(answer =='Y') {
				try {
					System.out.print("좌석 번호 선택 (");
					Iterator<Integer> itr = seatNos.iterator();
					while(itr.hasNext())
						System.out.print(itr.next() + " ");
					System.out.print("중 택1) : ");
					seatNo = CONSOLE.nextInt(); CONSOLE.nextLine();
					if(!seatNos.contains(seatNo) || seatNo <0 || seatNo >= reservations.length)
						throw new MenuChoiceException(seatNo);

					controller.getReservations()[seatNo-1] = true;

				} catch(MenuChoiceException e) {
					e.showWrongChoice();
					System.out.println(seatNo + "번 자리는 예약할 수 없습니다.");
				}
			}
			else if(answer =='N') {
				System.out.println("음식 포장을 선택하셨습니다.");
			}
			else {
				System.out.println("Y/N 으로 다시 입력해주세요.");
			}
		} while(answer!='Y' && answer!='N');

		return seatNo;
	}
	
}

package com.kh.food.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.kh.food.controller.UserController;
import com.kh.food.main.Main;
import com.kh.food.model.vo.MenuChoiceException;
import com.kh.food.model.vo.User;
import static com.kh.food.view.Constants.*;

public class MainMenu {
	public final static Scanner CONSOLE = new Scanner(System.in);
	static UserController controller = Main.getController();
	
	public static void showMainMenu() {
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

	public static void mainMenu() {
		int choice = -1;
		do {
			try {
				MainMenu.showMainMenu();
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
	
	public static User signUpView() {
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
				new HashMap<String, Integer>(), null, -1);
		return user;
	}

	public static String signInView() {
		System.out.println("=== 회원 로그인 ===");
		System.out.print("핸드폰번호 : ");
		String phone = CONSOLE.nextLine();

		return phone;
	}

	public static void showFoodMenu() {
		System.out.println("=== 음식 메뉴 ===");
		System.out.println("  1. 햄버거 --- 2,000");
		System.out.println("  2. 치킨 --- 9,000");
		System.out.println("=== 음료 메뉴 ===");
		System.out.println("  3. 콜라 --- 1,000");
		System.out.println("  4. 우유 --- 500");
		System.out.println("  0. 주문 종료");
		System.out.print(" 번호입력: ");
	}

	public static Map<String,Integer> orderView(){
		Map<String, Integer> orderList =new HashMap<String,Integer>();
		int total = 0;
		int choice = -1;
		int qty = 0;
		do {
			try {
				MainMenu.showFoodMenu();
				choice = CONSOLE.nextInt(); CONSOLE.nextLine();

				if(choice!=MENU_EXIT) {
					System.out.print("수량: ");
					qty = CONSOLE.nextInt(); CONSOLE.nextLine();
					if(qty <0)
						throw new MenuChoiceException(qty);
				}
				switch(choice) {
					case MENU_BURGER:
						orderList.put(BURGER, qty);
						total += PRICE_BURGER * qty;
						break;
					case MENU_CHICKEN:
						orderList.put(CHICKEN, qty);
						total += PRICE_CHICKEN * qty;
						break;
					case MENU_COKE:
						orderList.put(COKE, qty);
						total += PRICE_COKE * qty;
						break;
					case MENU_MILK:
						orderList.put(MILK, qty);
						total += PRICE_MILK * qty;
						break;
					case MENU_EXIT:
						System.out.println("주문을 완료하였습니다.");
						return orderList;
					default:
						throw new MenuChoiceException(choice);
				}
			} catch(MenuChoiceException e) {
				e.showWrongChoice();
				System.out.println("메뉴선택을 다시합니다.\n");
			}
		}while(choice!= 0);
		
		return new HashMap<String,Integer>();
	}
	
	public static void seatView() {
		System.out.println("테이블 현황(X: 빈좌석)");
		boolean[] reservations = controller.getReservations();
		
		for(int i=0;i<reservations.length; i++) {
			if(i==reservations.length/2)
				System.out.println();
			System.out.print((reservations[i]==true ? 'O':'X') + " ");
		}
		System.out.println();
	}

	public static int reserveSeatView() {
		MainMenu.seatView();
		boolean[] reservations = controller.getReservations();
		Set<Integer> seatNos = new HashSet<Integer>();
		
		for(int i=0;i<reservations.length; i++)
			if(reservations[i]==false) seatNos.add(i+1);

		char answer = '\u0000';
		int seatNo = -1;
		do {
			System.out.print("식사하고 가시겠습니까? (Y/N): ");
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
				System.out.println("Y/N으로 다시 입력해주세요.");
			}
		} while(answer!='Y' && answer!='N');

		return seatNo;
	}
	
	public int[] getFoodPrices() {
		return new int[] {PRICE_BURGER, PRICE_CHICKEN, PRICE_COKE, PRICE_MILK};
	}
}

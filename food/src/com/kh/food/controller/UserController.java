package com.kh.food.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.WriteAbortedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kh.food.model.vo.Food;
import com.kh.food.model.vo.User;
import com.kh.food.view.MainMenu;

public class UserController {
	private final File dataFile=new File("Users.dat"); //List<User> 저장용 파일
 
	private List<User> users = new ArrayList<User>(); //User 리스트

	private List<Food> foodMenu; //선택가능한 음식메뉴
	
	private String phone; //로그인 유저 폰번호 (User객체에 1:1맵핑)

	private MainMenu menu = new MainMenu(); //메뉴 선택을 위한 객체

	private final static int SEATS = 10; //좌석 수

	private boolean[] reservations = new boolean[SEATS]; //좌석 예약정보

	/* 메인메뉴 실행하기 */
	public void mainMenu() throws Exception {
		phone = null;
		this.readFromFile();
		this.loadDefaultFoodMenu();
		menu.mainMenu(this);
	}

	public void readFromFile() {
		if(dataFile.exists()==false)
			return;

		try (FileInputStream file=new FileInputStream(dataFile);
			ObjectInputStream in=new ObjectInputStream(file);){
			
			this.users = (ArrayList<User>)in.readObject();
			this.foodMenu = (ArrayList<Food>)in.readObject();
			
		} catch(WriteAbortedException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public void storeToFile() {

		try (FileOutputStream file=new FileOutputStream(dataFile);
			ObjectOutputStream out=new ObjectOutputStream(file);){
			
			out.writeObject(this.users);
			out.writeObject(this.foodMenu);
			
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void signUp() {
		if(this.phone != null)
			System.out.println("회원가입 하려면 먼저 로그아웃 해주세요.");

		User user = menu.signUpView();

		if(isDuplicate(user)) {
			System.out.println("이미 존재하는 회원 입니다.");
			return;
		}
		else {
			System.out.println("회원가입이 성공적으로 처리되었습니다.");
			users.add(user);
		}

		Collections.sort(this.users, (i,j)->{
			return i.getUsername().compareTo(j.getUsername());
		});
	}


	public void signIn() {
		String phoneInput = menu.signInView();
		if(phoneInput.equals(this.phone) || phone != null)
			System.out.println("이미 로그인 되어 있습니다.");

		User user = getUserByPhone(phoneInput);
		if(user==null) {
			System.out.println("로그인에 실패 하였습니다.");
		}
		else {
			user.setLogged(true);
			setPhone(phoneInput);
			System.out.println("로그인 됐슨니다.");
		}
	}
	
	public void signIn(String phoneTextField) {
		if(phoneTextField.equals(this.phone))
			System.out.println("이미 로그인 되어 있습니다.");

		User user = getUserByPhone(phoneTextField);
		if(user==null) {
			System.out.println("로그인에 실패 하였습니다.");
		}
		else {
			user.setLogged(true);
			setPhone(phoneTextField);
			System.out.println("로그인 됐슨니다.");
		}
	}

	public void logOff() {
		User user = null;
		Iterator<User>itr = users.iterator();
		while(itr.hasNext()) {	
			user = itr.next();
			if(user.getPhone().equals(this.phone)) {
				user.setLogged(false);
				if(user.getSeatNo() >=1 && user.getSeatNo() <=reservations.length) {
					reservations[user.getSeatNo() -1] = false;
					user.setSeatNo(0);
				}
				this.phone = null;
				System.out.println("로그아웃 됐습니다.");
			}
		}
	}

	public void order() {
		if (phone==null) {
			System.out.println("로그인 후 이용할 수 있습니다.");
			return;
		}
		
		//주문
		User user = getUserByPhone(this.phone);
		Map<Food, Integer> orderList = menu.orderView();
		user.setOrderList(orderList);

		//좌석
		if(orderList.size() > 0) {
			this.reserveSeat();
		
			//주문총액 및 주문내역 출력
			int total = 0;
			for(Map.Entry<Food, Integer> entry : orderList.entrySet()) {
				total += (entry.getValue() * entry.getKey().getMenuPrice());
				System.out.println("\t" + entry.getKey().getMenuName() + " ----- "
						+ entry.getKey().getMenuPrice() + " * " + entry.getValue() + "개");
			}

			System.out.println("주문하신 총액은 : " + total + "원 입니다.");
		}
		else
			System.out.println("주문을 취소하셨습니다.");
	}

	public void viewOrder() {
		if (phone==null) {
			System.out.println("로그인 후 이용할 수 있습니다.");
			return;
		}
		else {
			User user = getUserByPhone(phone);
			if(user!= null)
				user.showOrderList();
			else{
				System.out.println("해당하는 유저가 없습니다.");
			}
		}
	}

	public void reserveSeat() {
		int seatNo = menu.reserveSeatView();
		User user = getUserByPhone(this.phone);
		if(seatNo >=1 && seatNo <= reservations.length) {
			user.setSeatNo(seatNo);
		}
		if(user.getOrderList().size() > 0)
			user.setOrderCreated(new GregorianCalendar());
	}

	public void showUsers() {
		System.out.println("이름\t전화\t이메일\t주소\t로그인상태\t주문날짜");

		Iterator<User> itr = users.iterator();
		while(itr.hasNext()) {
			itr.next().showUserInfo();
		}
	}

	private void loadDefaultFoodMenu() {
		if(this.foodMenu != null && foodMenu.size() > 0)
			return;

		foodMenu = new ArrayList<Food>();
		foodMenu.add(new Food(1, "햄버거", 2000));
		foodMenu.add(new Food(2, "치킨", 9000));
		foodMenu.add(new Food(3, "콜라", 1000));
		foodMenu.add(new Food(4, "우유", 500));

		Collections.sort(foodMenu, (i,j)->{
			return i.getMenuNo() - j.getMenuNo();
		});
	}

	private User getUserByPhone(String phoneNum) {
		User user = null;
		Iterator<User> itr = users.iterator();
		while(itr.hasNext()) {
			user = itr.next();
			if(user.getPhone().equals(phoneNum)) {
				return user;
			}
		}
		return null;
	}

	private boolean isDuplicate(User user) {
		Set<User> set = new HashSet<User>();
		for (User u: users) set.add(u);
		return !set.add(user);
	}

	//getter setter
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }
	public boolean[] getReservations() { return reservations; }
	public void setReservations(boolean[] reservations) { this.reservations = reservations; }
	public static int getSeats() { return SEATS; }
	public List<Food> getFoodMenu() { return foodMenu; }
}

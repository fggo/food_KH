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
	//User 데이터 readFromFile, storeToFile
	private final File dataFile=new File("Users.dat"); 
 
	//User데이터 집합
	private List<User> users = new ArrayList<User>();

	private List<Food> foodMenu;
	
	//현재 로그인 유저 정보(User객체에 1:1맵핑)
	private String phone;

	//메뉴 선택을 위한 객체
	private MainMenu menu = new MainMenu(); 

	private final static int SEATS = 10; //좌석 수
	private boolean[] reservations = new boolean[SEATS]; //좌석 예약여부

	public void mainMenu() {
		phone = null;
		this.readFromFile();
		this.loadDefaultFoodMenu();
		menu.mainMenu(this);
	}

	public void readFromFile() {
		if(dataFile.exists()==false)
			return;
		try {
			FileInputStream file=new FileInputStream(dataFile);		
			ObjectInputStream in=new ObjectInputStream(file);
			
//			while(true) {
//				User user=(User)in.readObject();
//				if(user==null)
//					break;
//				users.add(user);
//			}
			this.users = (ArrayList<User>)in.readObject();
			this.foodMenu = (ArrayList<Food>)in.readObject();
			
			in.close();
		}
		catch(WriteAbortedException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public void storeToFile() {
		try {
			FileOutputStream file=new FileOutputStream(dataFile);		
			ObjectOutputStream out=new ObjectOutputStream(file);
			
//			Iterator<User> itr=users.iterator();
//			while(itr.hasNext())
//				out.writeObject(itr.next());
			out.writeObject(this.users);
			out.writeObject(this.foodMenu);
			
			out.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void signUp() {
		if(this.phone != null)
			System.out.println("회원가입 하려면 먼저 로그아웃 해주세요.");

		User user = menu.signUpView();

		Set<User> set = new HashSet<User>();
		for (User u: users) set.add(u);
		boolean isAdded = set.add(user);

		if(isAdded) {
			System.out.println("회원가입이 성공적으로 처리되었습니다.");
			users.add(user);
		}
		else {
			System.out.println("이미 존재하는 회원 입니다.");
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
			for(Food food : foodMenu) {
				total += (orderList.get(food) * food.getMenuPrice());
				System.out.println("\t" + food.getMenuName() + " ----- "
						+ food.getMenuPrice() + " * " + orderList.get(food) + "개");
			}

			System.out.println("주문하신 총액은 : " + total + "원 입니다.");
		}
		else
			System.out.println("주문을 취소하셨습니다.");
		
	}

	public void viewOrder() {
		
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
		foodMenu.add(new Food(1, "BURGER", 2000));
		foodMenu.add(new Food(2, "CHICKEN", 9000));
		foodMenu.add(new Food(3, "COKE", 1000));
		foodMenu.add(new Food(4, "MILK", 500));

		Collections.sort(foodMenu, (i,j)->{
			return i.getMenuNo() - j.getMenuNo();
		});
	}

	private <T> boolean hasDuplicate(Iterable<T> all) {
		Set<T> set = new HashSet<T>();
		// Set#add returns false if the set does not change, which
		// indicates that a duplicate element has been added.
		for (T each: all) if (!set.add(each)) return true;
		return false;
	}

	//getter setter
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }
	public boolean[] getReservations() { return reservations; }
	public void setReservations(boolean[] reservations) { this.reservations = reservations; }
	public static int getSeats() { return SEATS; }
	public List<Food> getFoodMenu() { return foodMenu; }
}

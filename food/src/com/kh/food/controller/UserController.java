package com.kh.food.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import com.kh.food.model.vo.User;
import com.kh.food.view.MainMenu;

public class UserController {
	//사용자 데이터 읽고(readFromFile),쓰기(storeToFile) 위한 파일
	private final File dataFile=new File("Users.dat"); 

	HashSet<User> users = new HashSet<User>();

	private String phone; //현재 로그인 유저 정보(User객체에 1:1맵핑 시킴)

	private MainMenu menu; //메뉴 선택을 위한 객체
	
	private final static int SEATS = 10;
	
	private boolean[] reservations = new boolean[SEATS];

	public UserController() {
		phone = null;
		readFromFile();
	}

	public void mainMenu() {
		menu = new MainMenu();
		MainMenu.mainMenu();
	}

	public void readFromFile() {
		if(dataFile.exists()==false)
			return;
		try {
			FileInputStream file=new FileInputStream(dataFile);		
			ObjectInputStream in=new ObjectInputStream(file);
			
			while(true) {
				User user=(User)in.readObject();
				if(user==null)
					break;
				users.add(user);
			}
			
			in.close();
		}
		catch(IOException e) {
			return;
		}
		catch(ClassNotFoundException e) {
			return;
		}
	}

	public void storeToFile() {
		try {
			FileOutputStream file=new FileOutputStream(dataFile);		
			ObjectOutputStream out=new ObjectOutputStream(file);
			
			Iterator<User> itr=users.iterator();
			while(itr.hasNext())
				out.writeObject(itr.next());
			
			out.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void signUp() {
		if(this.phone != null)
			System.out.println("회원가입 하려면 먼저 로그아웃 해주세요.");

		User user = MainMenu.signUpView();
		boolean isAdded = users.add(user);
		if(isAdded)
			System.out.println("회원가입이 성공적으로 처리되었습니다.");
		else {
			System.out.println("이미 존재하는 회원 입니다.");
		}
	}

	public void signIn() {
		String phoneInput = MainMenu.signInView();
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
		Map<String, Integer> orderList = MainMenu.orderView();
		user.setOrderList(orderList);

		//좌석
		this.reserveSeat();
		
		//주문총액 및 주문내역 출력
		int[] foodPrices = menu.getFoodPrices();
		int total = 0;
		int index = 0;
		for(Map.Entry<String, Integer> entry : orderList.entrySet()) {
			total += (entry.getValue()*foodPrices[index]);
			System.out.println("\t" + entry.getKey() + " ----- " 
					+ foodPrices[index++] +" * " + entry.getValue() + "개");
		}

		System.out.println("주문하신 총액은 : " + total + "원 입니다.");
	}

	public void viewOrder() {
		
	}

	public void reserveSeat() {
		int seatNo = MainMenu.reserveSeatView();
		User user = getUserByPhone(this.phone);
		if(seatNo >=1 && seatNo <= reservations.length) {
			user.setSeatNo(seatNo);
		}
		user.setOrderCreated(new GregorianCalendar());
	}

	public void showUsers() {
		System.out.println("이름\t전화\t이메일\t주소\t로그인상태\t주문날짜");

		Iterator<User> itr = users.iterator();
		while(itr.hasNext()) {
			itr.next().showUserInfo();
		}
	}

	//getter setter
	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }
	public boolean[] getReservations() { return reservations; }
	public void setReservations(boolean[] reservations) { this.reservations = reservations; }
	public static int getSeats() { return SEATS; }
}

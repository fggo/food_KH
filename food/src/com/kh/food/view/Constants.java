package com.kh.food.view;

import java.util.Scanner;

public final class Constants {
	private Constants() {
		//restrict initialization
	}

	/* 콘솔 입력 */
	public static final Scanner		CONSOLE		= new Scanner(System.in);


	/* 홈페이지 메뉴번호 */
	public static final int			SIGNUP				= 1;
	public static final int			SIGNIN				= 2;
	public static final int			LOGOFF				= 3;
	public static final int			ORDER				= 4;
	public static final int			VIEW_ORDER			= 5;
	public static final int			SHOW_USERS			= 6;
	
	public static final int			MENU_EXIT			= 0;
	

	/* 음식 메뉴 정보 */
	//음식 메뉴번호
	public static final int			MENU_BURGER			= 1;
	public static final int			MENU_CHICKEN		= 2;
	public static final int			MENU_COKE			= 3;
	public static final int			MENU_MILK			= 4;
	//음식 이름
	public static final String		BURGER				= "BURGER";
	public static final String		CHICKEN				= "CHICKEN";
	public static final String		COKE				= "COKE";
	public static final String		MILK				= "MILK";
	//음식 가격
	public static final int			PRICE_BURGER		= 2000;
	public static final int			PRICE_CHICKEN		= 9000;
	public static final int			PRICE_COKE			= 1000;
	public static final int			PRICE_MILK			= 500;


	/* User 로그인 상태 */
	public static final boolean		OFF					= false;
	public static final boolean		ON					= true;
}

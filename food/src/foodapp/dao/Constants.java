package foodapp.dao;

import java.util.Scanner;

public final class Constants {
	private Constants() {
		//restrict initialization
	}

	/* 홈페이지 메뉴번호 */
	public static final int 		WINDOW_WIDTH 		= 900;
	public static final int 		WINDOW_HEIGHT 		= 650;

	public static final String 		INIT_PAGE 			= "Card - Home Page";
	public static final String 		FOOD_MENU_PAGE 		= "Card - Food Menu";
	public static final String 		ORDER_VIEW_PAGE 	= "Card - View Order";
	public static final String 		SIGN_UP_PAGE 		= "Card - Sign Up";
	public static final String 		ADMIN_PAGE 			= "Card - Admin Page";


	/* User 로그인 상태 */
	public static final boolean		OFF					= false;
	public static final boolean		ON					= true;
}

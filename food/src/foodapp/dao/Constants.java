package foodapp.dao;

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
	

	public static final int 		WINDOW_WIDTH 		= 900;
	public static final int 		WINDOW_HEIGHT 		= 650;

	public static final String 		INIT_PAGE 			= "Card - Home Page";
	public static final String 		FOOD_MENU_PAGE 		= "Card - Food Menu";
	public static final String 		ORDER_VIEW_PAGE 	= "Card - View Order";
	public static final String 		SIGN_UP_PAGE 		= "Card - Sign Up";
	public static final String 		MY_PAGE 			= "Card - My Page";


	/* User 로그인 상태 */
	public static final boolean		OFF					= false;
	public static final boolean		ON					= true;
}

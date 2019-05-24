package com.kh.food.gui;

import javax.swing.*;

public class InitPageFrame extends JFrame{

	JButton signUpBtn = new JButton("회원가입");
	JButton signInBtn = new JButton("로그인");
	JButton logOffBtn = new JButton("로그아웃");
	JButton orderBtn = new JButton("주문하기");
	JButton orderViewBtn = new JButton("주문조회");
	JButton menuBtn = new JButton("메뉴");
	JButton myPageBtn = new JButton("마이페이지");
	JButton adminPageBtn = new JButton("관리자");

	JTextField phone;

	public InitPageFrame() {
		// TODO Auto-generated constructor stub
		add(signUpBtn);
		add(signInBtn);
		add(logOffBtn);
	
		
		setVisible(true);
	}
	

}

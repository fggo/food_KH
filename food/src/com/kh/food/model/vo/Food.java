package com.kh.food.model.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Food implements Serializable, Comparable<Food> {

	private int menuNo;
	private String menuName;
	private int menuPrice;


	public Food() {
	}	
	
	public Food(int menuNo, String menuName, int menuPrice) {
		super();
		this.menuNo = menuNo;
		this.menuName = menuName;
		this.menuPrice = menuPrice;
	}

	@Override
	public int compareTo(Food o) {
		return this.menuNo - o.getMenuNo();
	}
	
	@Override
	public boolean equals(Object obj) {
		Food food = (Food)obj;
		return this.menuNo == food.getMenuNo();
	}

	public int getMenuNo() { return menuNo; }
	public void setMenuNo(int menuNo) { this.menuNo = menuNo; }
	public String getMenuName() { return menuName; } 
	public void setMenuName(String menuName) { this.menuName = menuName; } 
	public int getMenuPrice() { return menuPrice; } 
	public void setMenuPrice(int menuPrice) { this.menuPrice = menuPrice; }


}

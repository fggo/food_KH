package com.kh.food.model.vo;

public class MenuChoiceException extends Exception {
	private int wrongChoice;

	public MenuChoiceException(int wrongChoice) {
		super("잘못된 번호 선택입니다.");
		this.wrongChoice = wrongChoice;
	}

	public void showWrongChoice() {
		System.out.println(wrongChoice + "에 해당하는 선택은 존재하지 않습니다.");
	}

	public int getWrongChoice() { return wrongChoice; }
	public void setWrongChoice(int wrongChoice) { this.wrongChoice = wrongChoice; }
}

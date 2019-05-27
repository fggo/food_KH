package com.kh.food.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class SignInEventHandler implements ActionListener {
	private JTextField phoneField;


	public SignInEventHandler(JTextField phoneField) {
		super();
		this.phoneField = phoneField;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String name=phoneField.getText();
	}
	
}

package com.kh.food.main;

import com.kh.food.controller.UserController;

public class Main {
	private static UserController controller = new UserController();

	public static UserController getController() {
		return controller;
	}
	public static void main(String[] args) {
		Main.getController().mainMenu();

//		Runtime.getRuntime().addShutdownHook(
//			new Thread() { 
//				public void run() { 
//					System.out.println("Shutdown Hook is running !"); 
//				}
//			}
//		); 
//		System.out.println("Application Terminating ..."); 		}
	}
}

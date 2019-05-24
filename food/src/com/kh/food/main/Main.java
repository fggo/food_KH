package com.kh.food.main;

import com.kh.food.controller.UserController;

public class Main {
	public static void main(String[] args) {
		new UserController().mainMenu();

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

package foodapp.model.vo;

import java.util.GregorianCalendar;
import java.util.Map;

public class Admin extends User{

	public Admin(String username, String password, String phone, String email, String address, boolean logged,
			Map<Food, Integer> orderList, GregorianCalendar orderCreated, int seatNo) {
		super(username, password, phone, email, address, logged, orderList, orderCreated, seatNo);
		// TODO Auto-generated constructor stub
	}

	
}

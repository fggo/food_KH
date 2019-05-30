package foodapp.model.vo;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Admin extends User{
	//메뉴, 판매량
	private Map<Food, Integer> salesResult;

	public Admin(String username, String password, String phone, String email, String address, boolean logged,
			Map<Food, Integer> orderList, GregorianCalendar orderCreated, int seatNo, Map<Food, Integer> salesResult) {

		super(username, password, phone, email, address, logged, orderList, orderCreated, seatNo);
		this.salesResult = salesResult;
	}


}

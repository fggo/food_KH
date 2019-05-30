package foodapp.gui.event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import foodapp.dao.UserRepository;
import foodapp.model.vo.Food;
import foodapp.model.vo.User;

public class PaymentClickEvent extends MouseAdapter {
	private JTextField phoneTextField;
	private JComboBox menuChoiceComboBox;
	private JComboBox menuQtyComboBox;
	private UserRepository userRepo;

	public PaymentClickEvent(JTextField phoneTextField, JComboBox menuChoiceComboBox, JComboBox menuQtyComboBox, 
			UserRepository userRepo) {
		super();
		this.phoneTextField = phoneTextField;
		this.menuChoiceComboBox = menuChoiceComboBox;
		this.menuQtyComboBox = menuQtyComboBox;
		this.userRepo = userRepo;
	}
	//userRepo- user
	// qty
	//food menu
	//->admin data
	//user data update

	public void mouseClicked(MouseEvent e) {
		if(!this.phoneTextField.isEditable()) {
			System.out.println("로그인을 먼저 해주세요.");
			return;
		}
		
		User user = getUserByPhone(this.phoneTextField.getText());
		if(!user.isLogged()) {
			System.out.println("로그인 데이터 에러 \n"
					+"logged : OFF.상태에서 주문할 수 없습니다. ");
			return;
		}

		Food food = (Food)this.menuChoiceComboBox.getSelectedItem();
		int qty = (Integer)this.menuQtyComboBox.getSelectedItem();

		Object source = e.getSource();
		System.out.println(source);
		if (source instanceof JButton) {
			String name = ((JButton) e.getSource()).getName();
			switch(name) {
				case "CASH":
					break;
				case "CARD":
					break;
			}
		}
	}
	private User getUserByPhone(String phoneNum) {
		User user = null;
		Iterator<User> itr = userRepo.getUsers().iterator();

		while(itr.hasNext()) {
			user = itr.next();
			if(user.getPhone().equals(phoneNum)) {
				return user;
			}
		}
		
		
		return null;
	}

}

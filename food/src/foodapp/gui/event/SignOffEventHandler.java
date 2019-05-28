package foodapp.gui.event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextField;

import foodapp.dao.UserRepository;
import foodapp.model.vo.User;

public class SignOffEventHandler extends MouseAdapter {
	private JTextField loggedPhoneTextField;
	private UserRepository userRepo;

	public SignOffEventHandler(JTextField loggedPhoneTextField, UserRepository userRepo) {
		super();

		this.loggedPhoneTextField = loggedPhoneTextField;
		this.userRepo = userRepo;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (loggedPhoneTextField != null) {
			String phoneNum = loggedPhoneTextField.getSelectedText().replaceAll("\\s+", "");
			if(phoneNum.length() > 0 ) {
				getUserByPhone(phoneNum).setLogged(false);
				loggedPhoneTextField.setEditable(true);
				loggedPhoneTextField.setText("");
			}
		}
	}
	
	private User getUserByPhone(String phone) {
		List<User> users = new ArrayList<User>();
		Iterator<User> itr = users.iterator();
		User user = null;
		while(itr.hasNext()) {
			user = itr.next();
			if(user.getPhone().equals(phone))
				return user;
		}

		return null;
	}
}

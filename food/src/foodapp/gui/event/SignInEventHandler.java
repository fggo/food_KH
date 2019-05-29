package foodapp.gui.event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextField;

import foodapp.dao.UserRepository;
import foodapp.model.vo.User;

public class SignInEventHandler extends MouseAdapter {
	private JTextField phoneTextField;
	private UserRepository userRepo;

	public SignInEventHandler(JTextField phoneTextField, UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
		this.phoneTextField = phoneTextField;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (phoneTextField == null) {
			return;
		}

		String phoneNum = phoneTextField.getText().replaceAll("\\s+", "");
		boolean logged = false;
		if (phoneNum.length() > 0) logged = signIn(phoneNum);
		
		if(logged) {
			phoneTextField.setEditable(false);
		}
		else {
			System.out.println("로그인에 실패하였습니다.");
			phoneTextField.setText("");
			return;
		}
	}
	
	private boolean signIn(String phone) {
		User user = getUserByPhone(phone);

		if(user != null) {
			user.setLogged(true);
			userRepo.setPhone(phone); // mapping UserController and User
			return true;
		}
		else {
			return false;
		}
	}
	
	private User getUserByPhone(String phone) {
		List<User> users = userRepo.getUsers();
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

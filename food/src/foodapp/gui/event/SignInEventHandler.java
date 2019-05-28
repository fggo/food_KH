package foodapp.gui.event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextField;

import com.kh.food.model.vo.User;

import foodapp.dao.UserRepository;

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

		String phoneNum = phoneTextField.getSelectedText().replaceAll("\\s+", "");
		boolean logged = false;
		if (phoneNum.length() > 0) logged = signIn(phoneNum);
		
		if(logged) {
			phoneTextField.setEditable(false);
		}
	}
	
	private boolean signIn(String phone) {
		User user = getUserByPhone(phone);
		userRepo.setPhone(phone); // mapping UserController and User

		if(user != null) {
			user.setLogged(true);
			return true;
		}
		return false;
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

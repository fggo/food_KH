package foodapp.gui.event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import foodapp.dao.UserRepository;

public class SignInEventHandler extends MouseAdapter {
	private JTextField phoneTextField;
	private JPasswordField passwordField;
	private UserRepository userRepo;

	public SignInEventHandler(JTextField phoneTextField, JPasswordField passwordField, UserRepository userRepo) {
		super();
		this.phoneTextField = phoneTextField;
		this.passwordField = passwordField;
		this.userRepo = userRepo;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (phoneTextField == null) {
			return;
		}
		if(!phoneTextField.isEditable()) {
			System.out.println("이미 로그인 되어 있습니다.");
			return;
		}

		String phoneNum = phoneTextField.getText().replaceAll("\\s+", "");
		String password = new String(passwordField.getPassword());
		boolean logged = false;
		if (phoneNum.length() > 0)
			logged = userRepo.signIn(phoneNum, password);
		
		if(logged) {
			phoneTextField.setEditable(false);
			passwordField.setText("");
			passwordField.setEditable(false);
		}
		else {
			System.out.println("로그인에 실패하였습니다.");
			phoneTextField.setText("");
			passwordField.setText("");
		}
	}
	
}

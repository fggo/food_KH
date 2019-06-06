package foodapp.gui.event;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import foodapp.dao.UserRepository;

public class SignOffEventHandler extends MouseAdapter {
	private CardLayout cl;
	private JPanel userCards;
	private JTextField loggedPhoneTextField;
	private JPasswordField passwordField;
	private UserRepository userRepo;

	public SignOffEventHandler(CardLayout cl, JPanel userCards, JTextField loggedPhoneTextField, JPasswordField passwordField, UserRepository userRepo) {
		super();

		this.cl = cl;
		this.userCards = userCards;
		this.loggedPhoneTextField = loggedPhoneTextField;
		this.passwordField = passwordField;
		this.userRepo = userRepo;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (loggedPhoneTextField != null) {
			String phoneNum = loggedPhoneTextField.getText().replaceAll("\\s+", "");
			if(phoneNum.length() > 0 ) {
				userRepo.logOff(phoneNum);
				loggedPhoneTextField.setEditable(true);
				loggedPhoneTextField.setText("");
				passwordField.setEditable(true);
				passwordField.setText("");
				cl.show(userCards, "USER_LOGOFF");
			}
		}
	}
	
}

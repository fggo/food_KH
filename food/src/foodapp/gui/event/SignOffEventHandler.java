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
				userRepo.logOff(phoneNum);
				loggedPhoneTextField.setEditable(true);
				loggedPhoneTextField.setText("");
			}
		}
	}
	
}

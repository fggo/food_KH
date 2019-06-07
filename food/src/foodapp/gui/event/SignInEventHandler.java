package foodapp.gui.event;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import foodapp.dao.UserRepository;
import foodapp.model.vo.User;

public class SignInEventHandler extends MouseAdapter {
	private CardLayout cl;
	private JPanel userCards;
	private JButton adminPageBtn;
	private JTextField phoneTextField;
	private JPasswordField passwordField;

	private UserRepository userRepo;

	public SignInEventHandler(CardLayout cl, JPanel userCards, JButton adminPageBtn,
			JTextField phoneTextField, JPasswordField passwordField, UserRepository userRepo) {
		super();
		this.cl = cl;
		this.userCards = userCards;
		this.adminPageBtn = adminPageBtn;
		this.phoneTextField = phoneTextField;
		this.passwordField = passwordField;
		this.userRepo = userRepo;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		userRepo.showUsers();

		if (phoneTextField == null) {
			return;
		}
		if(!phoneTextField.isEditable() || !passwordField.isEditable()) {
			System.out.println("이미 로그인 되어 있습니다.");
			JOptionPane.showMessageDialog(null, "이미 로그인 되어 있습니다.", "로그인 확인", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String phoneNum = phoneTextField.getText().replaceAll("\\s+", "");
		String password = new String(passwordField.getPassword());
		User user = null;
		if (phoneNum.length() > 0)
			user = userRepo.signIn(phoneNum, password);
		
		if(user!=null) {
			phoneTextField.setEditable(false);
			passwordField.setText("");
			passwordField.setEditable(false);
			if(user.equals(userRepo.getAdmin()))
				this.adminPageBtn.setVisible(true);
			else
				this.adminPageBtn.setVisible(false);

			JOptionPane.showMessageDialog(null, "로그인을 성공했습니다.", "로그인 확인", JOptionPane.WARNING_MESSAGE);
			cl.show(userCards, "USER_LOGGED");
		}
		else {
			JOptionPane.showMessageDialog(null, "로그인 정보가 불일치 합니다.", "로그인 확인", JOptionPane.WARNING_MESSAGE);
			phoneTextField.setText("");
			passwordField.setText("");
		}
	}
	
}

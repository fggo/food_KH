package foodapp.gui.layout;

import static foodapp.dao.Constants.INIT_PAGE;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SignUpPageFrame extends JPanel implements ActionListener {
	private JButton homeBtn;
	private CardLayout cl;
	private JPanel cards;

	JTextField username, phone, email, address;
	JLabel usernameLabel, phoneLabel, emailLabel, addressLabel;
	JButton confirmBtn;
	
//		User user = new User(username, phone, email, address, OFF,
//				new TreeMap<Food, Integer>(), null, -1);
	
	public SignUpPageFrame(CardLayout cl, JPanel cards) {
		this.cl = cl;
		this.cards = cards;

		initialize();
	}

	private void initialize() {
		homeBtn = new JButton("홈으로");
		homeBtn.addActionListener(this);

		username = new JTextField(10);
		phone = new JTextField(11);
		email = new JTextField(20);
		address = new JTextField(50);
		
		usernameLabel = new JLabel("이름");
		phoneLabel = new JLabel("전화");
		emailLabel = new JLabel("이메일");
		addressLabel = new JLabel("주소");

		confirmBtn = new JButton("확인");
		
		add(homeBtn);
		add(usernameLabel); add(username); 
		add(phoneLabel); add(phone);
		add(emailLabel); add(email);
		add(addressLabel); add(address);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		cl.previous(cards);
		cl.show(cards, INIT_PAGE);
	}

}
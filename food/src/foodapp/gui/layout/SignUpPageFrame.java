package foodapp.gui.layout;

import static foodapp.dao.Constants.INIT_PAGE;
import static foodapp.dao.Constants.OFF;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import foodapp.dao.UserRepository;
import foodapp.model.vo.Food;
import foodapp.model.vo.User;


public class SignUpPageFrame extends JPanel implements ActionListener {
	private CardLayout cl;
	private JPanel cards;

	private JButton homeBtn;
	private JTextField username, phone, email, address;
	private JLabel usernameLabel, phoneLabel, emailLabel, addressLabel;
	private JButton confirmBtn;
	
	private UserRepository userRepo;
	
//		User user = new User(username, phone, email, address, OFF,
//				new TreeMap<Food, Integer>(), null, -1);
	
	public SignUpPageFrame(CardLayout cl, JPanel cards, UserRepository userRepo) {
		this.userRepo = userRepo;
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

		
		add(homeBtn);
		add(usernameLabel); add(username); 
		add(phoneLabel); add(phone);
		add(emailLabel); add(email);
		add(addressLabel); add(address);

		confirmBtn = new JButton("확인");
		confirmBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				userRepo.showUsers();
				if(username.getText().equals("")
						|| phone.getText().equals("")
						|| email.getText().equals("")
						|| address.getText().equals("")) {
					System.out.println("일부 필드값이 입력이 안됐습니다.");
					return;
				}
				User user = new User(username.getText(), phone.getText(), 
						email.getText(), address.getText(), OFF,
						new TreeMap<Food, Integer>(), null, -1);

				if(userRepo.getPhone() != null) {
					System.out.println("회원가입 하려면 먼저 로그아웃 해주세요.");
					return;
				}
				userRepo.signUp(user);
				userRepo.showUsers();
			}
		});

		add(confirmBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cl.show(cards, INIT_PAGE);
	}

	private boolean isDuplicate(User user) {
		Set<User> set = new HashSet<User>();
		for (User u: userRepo.getUsers()) set.add(u);
		return !set.add(user);
	}

}
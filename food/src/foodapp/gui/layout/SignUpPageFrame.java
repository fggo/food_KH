package foodapp.gui.layout;

import static foodapp.dao.Constants.INIT_PAGE;
import static foodapp.dao.Constants.OFF;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import foodapp.dao.UserRepository;
import foodapp.model.vo.Food;
import foodapp.model.vo.User;


public class SignUpPageFrame extends JPanel implements ActionListener {
	private CardLayout cl;
	private JPanel cards;

	private JButton homeBtn;
	private JTextField username, phone, email, address;
	private JPasswordField password;
	private JLabel usernameLabel, passwordLabel, phoneLabel, emailLabel, addressLabel;
	private JButton confirmBtn;
	
	private JPanel p1,p2,p3,p4,p5,p6;

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
		setLayout(new GridLayout(6,1));
		p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p6 = new JPanel(new FlowLayout(FlowLayout.CENTER));

		homeBtn = new JButton("홈으로");
		homeBtn.addActionListener(this);

		username = new JTextField(10);
		phone = new JTextField(11);
		email = new JTextField(20);
		address = new JTextField(50);
		password = new JPasswordField(10);
		
		usernameLabel = new JLabel("이름");
		phoneLabel = new JLabel("전화");
		emailLabel = new JLabel("이메일");
		addressLabel = new JLabel("주소");
		passwordLabel = new JLabel("비밀번호");

		
		p1.add(homeBtn);
		p1.add(usernameLabel); p1.add(username); 
		p2.add(phoneLabel); p2.add(phone);
		p3.add(passwordLabel); p3.add(password); 
		p4.add(emailLabel); p4.add(email);
		p5.add(addressLabel); p5.add(address);

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
				User user = new User(username.getText(), new String(password.getPassword()),
						phone.getText(), email.getText(), address.getText(), OFF,
						new TreeMap<Food, Integer>(), null, -1);

				if(userRepo.getPhone() != null) {
					System.out.println("회원가입 하려면 먼저 로그아웃 해주세요.");
					return;
				}
				userRepo.signUp(user, new String(password.getPassword()));

				username.setText("");
				phone.setText("");
				password.setText("");
				email.setText("");
				address.setText("");

				userRepo.showUsers();
			}
		});

		p6.add(confirmBtn);
		
		add(p1);
		add(p2);
		add(p3);
		add(p4);
		add(p5);
		add(p6);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cl.show(cards, INIT_PAGE);
	}
}
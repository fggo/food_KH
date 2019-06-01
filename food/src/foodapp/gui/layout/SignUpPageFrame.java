package foodapp.gui.layout;

import static foodapp.dao.Constants.INIT_PAGE;
import static foodapp.dao.Constants.OFF;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import foodapp.dao.UserRepository;
import foodapp.model.vo.User;


public class SignUpPageFrame extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CardLayout cl;
	private JPanel cards;

	private JTextField username, phone, email, address;
	private JPasswordField password;
	private JLabel usernameLabel, passwordLabel1, passwordLabel2, sub1, sub2, phoneLabel, emailLabel, addressLabel;
	private JButton confirmBtn;
	
	private JPanel l1,l2,l3,l4,l5;
	private JPanel r1,r2,r3,r4,r5;
	private JPanel c6;
	
	private JPanel signUpLeftPanel;
	private JPanel receiptLabelPanel;
	private JLabel receiptLabel;
	private JPanel receiptTextArea;

	private JSplitPane centerReceipt;
	private JSplitPane signUpSplitPane1;
	private JSplitPane signUpSplitPane2;
	private JSplitPane signUpSplitPane3;
	
	private JSplitPane s1,s2,s3,s4,s5;

	private JPanel signUpRightPanel;
	private JPanel backButtonPanel;

	private JButton signUpHomeBtn;

	private UserRepository userRepo;
	
	public SignUpPageFrame(CardLayout cl, JPanel cards, UserRepository userRepo) {
		this.userRepo = userRepo;
		this.cl = cl;
		this.cards = cards;

		initialize();
	}

	private void initialize() {

		setLayout(new BorderLayout());

		signUpLeftPanel = new JPanel();
		centerReceipt = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		receiptLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		receiptLabel = new JLabel("회원 가입");
		receiptLabelPanel.add(receiptLabel);
		receiptTextArea = new JPanel(new GridLayout(6,1));
		
		s1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		s2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		s3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		s4 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		s5 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		
		l1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		l2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		l3 = new JPanel(new GridLayout(2,1));
		l4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		l5 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		r1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		r2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		r3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		r4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		r5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		c6 = new JPanel(new BorderLayout());
		
		s1.setLeftComponent(l1); s1.setRightComponent(r1);
		s2.setLeftComponent(l2); s2.setRightComponent(r2);
		s3.setLeftComponent(l3); s3.setRightComponent(r3);
		s4.setLeftComponent(l4); s4.setRightComponent(r4);
		s5.setLeftComponent(l5); s5.setRightComponent(r5);

		receiptTextArea.add(s1);
		receiptTextArea.add(s2);
		receiptTextArea.add(s3);
		receiptTextArea.add(s4);
		receiptTextArea.add(s5);
		receiptTextArea.add(c6);

		centerReceipt.setTopComponent(receiptLabelPanel);
		centerReceipt.setBottomComponent(receiptTextArea);

		signUpSplitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);


		signUpSplitPane1.setLeftComponent(signUpLeftPanel);
		signUpSplitPane1.setRightComponent(centerReceipt);

		signUpSplitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		signUpRightPanel = new JPanel();
		signUpSplitPane2.setLeftComponent(signUpSplitPane1);
		signUpSplitPane2.setRightComponent(signUpRightPanel);
		
		signUpSplitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		signUpHomeBtn = new JButton("뒤로 가기");
		signUpHomeBtn.setName(INIT_PAGE);
		signUpHomeBtn.addActionListener(this);
		backButtonPanel.add(signUpHomeBtn);
		signUpSplitPane3.setTopComponent(backButtonPanel);
		signUpSplitPane3.setBottomComponent(signUpSplitPane2);
		
		invokeSplitPane();
		
		

		username = new JTextField(20);
		phone = new JTextField(20);
		email = new JTextField(20);
		address = new JTextField(34);
		password = new JPasswordField(20);
		
		usernameLabel = new JLabel("이름");
		phoneLabel = new JLabel("전화");
		emailLabel = new JLabel("이메일");
		addressLabel = new JLabel("주소");
		passwordLabel1 = new JLabel("비밀번호");
		passwordLabel2 = new JLabel("(4자리 이상)");

		
		l1.add(usernameLabel); 	r1.add(username); 
		l2.add(phoneLabel); 	r2.add(phone);
		JPanel sub1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		sub1.add(passwordLabel1);
		JPanel sub2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		sub2.add(passwordLabel2);
		l3.add(sub1); l3.add(sub2);
								r3.add(password); 
		l4.add(emailLabel); 	r4.add(email);
		l5.add(addressLabel); 	r5.add(address);

		confirmBtn = new JButton("가입 하기");
		confirmBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(username.getText().equals("")
						|| phone.getText().equals("")
						|| email.getText().equals("")
						|| address.getText().equals("")
						|| phone.getText().length() < 4){
					System.out.println("일부 필드값이 입력이 잘못됐습니다.");
					return;
				}
				else if(phone.getText().length() < 4){
					System.out.println("비밀번호는 4자리 이상으로 해주세요.");
				}
				User user = new User(username.getText(), new String(password.getPassword()),
						phone.getText(), email.getText(), address.getText(), OFF,
						null, null, null, -1, false);

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

		c6.add(confirmBtn);
		
		add(signUpSplitPane3);
	}

	private void invokeSplitPane() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                signUpSplitPane1.setDividerLocation(200 + signUpSplitPane1.getInsets().left);
                signUpSplitPane2.setDividerLocation(675+signUpSplitPane2.getInsets().left);
                signUpSplitPane3.setDividerLocation(40+signUpSplitPane3.getInsets().top);
                centerReceipt.setDividerLocation(30+centerReceipt.getInsets().top);

                signUpSplitPane1.setEnabled(false);
                signUpSplitPane1.setDividerSize(1);

                signUpSplitPane2.setEnabled(false);
                signUpSplitPane2.setDividerSize(1);

                signUpSplitPane3.setEnabled(false);
                signUpSplitPane3.setDividerSize(1);
                
                centerReceipt.setEnabled(false);
                centerReceipt.setDividerSize(1);
                
                int sLeftOffSet = 80;
                
                s1.setDividerLocation(sLeftOffSet + s1.getInsets().left);
                s2.setDividerLocation(sLeftOffSet + s2.getInsets().left);
                s3.setDividerLocation(sLeftOffSet + s3.getInsets().left);
                s4.setDividerLocation(sLeftOffSet + s4.getInsets().left);
                s5.setDividerLocation(sLeftOffSet + s5.getInsets().left);
                
                s1.setEnabled(false);
                s2.setEnabled(false);
                s3.setEnabled(false);
                s4.setEnabled(false);
                s5.setEnabled(false);
                
                s1.setDividerSize(1);
                s2.setDividerSize(1);
                s3.setDividerSize(1);
                s4.setDividerSize(1);
                s5.setDividerSize(1);
            }
        });
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cl.show(cards, INIT_PAGE);
	}
}
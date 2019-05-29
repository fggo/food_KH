package foodapp.gui.layout;

import static foodapp.dao.Constants.FOOD_MENU_PAGE;
import static foodapp.dao.Constants.INIT_PAGE;
import static foodapp.dao.Constants.MY_PAGE;
import static foodapp.dao.Constants.ORDER_PAGE;
import static foodapp.dao.Constants.ORDER_VIEW_PAGE;
import static foodapp.dao.Constants.SIGN_UP_PAGE;
import static foodapp.dao.Constants.WINDOW_HEIGHT;
import static foodapp.dao.Constants.WINDOW_WIDTH;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import org.openide.awt.DropDownButtonFactory;

import foodapp.dao.UserRepository;
import foodapp.gui.event.HomeBtnEventHandler;
import foodapp.gui.event.MenuItemEventHandler;
import foodapp.gui.event.MenuPageBtnEventHandler;
import foodapp.gui.event.OrderBtnEvenHandler;
import foodapp.gui.event.OrderViewBtnEventHandler;
import foodapp.gui.event.SignInEventHandler;
import foodapp.gui.event.SignOffEventHandler;
import foodapp.model.vo.Food;


public class InitPageFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JPanel cards;
	private JPanel card1, card2, card3, card4, card5, card6;
	
	private JPanel topPanel, bottomPanel, rightPanel, leftPanel, subPanel1, subPanel2;
	
	private JButton logoBtn, menuDropDownBtn, myPageBtn, orderViewBtn;
	private JButton signInBtn1, signInBtn2, signUpBtn1, signUpBtn2;
	private JButton logOffBtn1, logOffBtn2;
	private JButton orderBtn;
	
	private JButton backBtn;
	
	private JTextField phoneTextField;
	
	private UserRepository userRepo = null;

	/**
	 * Create the application.
	 */
	public InitPageFrame(UserRepository userRepo) throws Exception {
		this.userRepo = userRepo;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws Exception {

		CardLayout cl = new CardLayout();
		cards = new JPanel(cl);

		card1 = new JPanel(new BorderLayout());
		card2 = new JPanel(new BorderLayout());
		card3 = new JPanel(new BorderLayout());
		card4 = new JPanel(new BorderLayout());
		card5 = new JPanel(new BorderLayout());
		card6 = new JPanel(new BorderLayout());

		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocationRelativeTo(null); //center window
		getContentPane().setLayout(new BorderLayout());


		/* 상단 메뉴 바 */
		this.createTopMenuBar();

		/* 화면 split */
		splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane1.setDividerLocation(300 + splitPane1.getInsets().top);

		phoneTextField = new JTextField("", 11); //핸드폰 11자리
		signInBtn1 = new JButton("로그인");
		signUpBtn1 = new JButton("회원가입");
		logOffBtn1 = new JButton("로그아웃");
		orderBtn = new JButton("주문하기");
		orderBtn.setName(ORDER_PAGE);
		signUpBtn1.setName(SIGN_UP_PAGE);


		leftPanel = new JPanel();
		rightPanel = new JPanel(new GridLayout(4,1));
		subPanel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
		subPanel1.add(new JLabel("핸드폰 번호"));
		subPanel1.add(phoneTextField);
		subPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		subPanel2.add(signInBtn1);
		subPanel2.add(signUpBtn1);
		subPanel2.add(logOffBtn1);
		rightPanel.add(new JLabel());
		rightPanel.add(subPanel1);
		rightPanel.add(subPanel2);

		topPanel = new JPanel(new GridLayout(1,3));
		topPanel.add(leftPanel);
		topPanel.add(new JPanel());
		topPanel.add(rightPanel);
		bottomPanel = new JPanel(new GridLayout(2,1));
		bottomPanel.add(new JPanel());
		bottomPanel.add(orderBtn);
		splitPane1.setTopComponent(topPanel);
		splitPane1.setBottomComponent(bottomPanel);
		splitPane1.setEnabled(false);

		/* 네비게이션 메뉴 */
		JToolBar navBar = this.createNavBar();

		splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane2.setDividerLocation(70 + splitPane2.getInsets().top);

		topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(navBar);
		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(splitPane1);
		splitPane2.setTopComponent(topPanel);
		splitPane2.setBottomComponent(bottomPanel);
		splitPane2.setEnabled(false);

		/* 맨위 패널 */
		JPanel topMostPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		signInBtn2 = new JButton("로그인");
		signUpBtn2 = new JButton("회원가입");
		logOffBtn2 = new JButton("로그아웃");
		signUpBtn2.setName(SIGN_UP_PAGE);
		topMostPanel.add(signInBtn2);
		topMostPanel.add(signUpBtn2);
		topMostPanel.add(logOffBtn2);
		splitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane3.setDividerLocation(30 + splitPane3.getInsets().top);

		topPanel = topMostPanel;
		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(splitPane2);
		splitPane3.setTopComponent(topPanel);
		splitPane3.setBottomComponent(bottomPanel);
		splitPane3.setEnabled(false);

		card1.add(splitPane3);
		card2.add(new FoodMenuPageFrame(cl, cards));
		card3.add(new OrderViewPageFrame(cl, cards));
		card4.add(new MyPageFrame(cl, cards));
		card5.add(new OrderPageFrame(cl, cards));
		card6.add(new SignUpPageFrame(cl, cards, userRepo));

		cards.add(card1, INIT_PAGE);
		cards.add(card2, FOOD_MENU_PAGE);
		cards.add(card3, ORDER_VIEW_PAGE);
		cards.add(card4, MY_PAGE);
		cards.add(card5, ORDER_PAGE);
		cards.add(card6, SIGN_UP_PAGE);

		getContentPane().add(cards);
		
		/* 각 component에 이벤트 추가 */
		/* 클릭이벤트 */
		logoBtn.addMouseListener(new HomeBtnEventHandler());

		signInBtn1.addMouseListener(new SignInEventHandler(phoneTextField, userRepo));
		signInBtn2.addMouseListener(new SignInEventHandler(phoneTextField, userRepo));

		logOffBtn1.addMouseListener(new SignOffEventHandler(phoneTextField, userRepo));
		logOffBtn2.addMouseListener(new SignOffEventHandler(phoneTextField, userRepo));
		
		signUpBtn1.addActionListener(this);
		signUpBtn2.addActionListener(this);
		
		menuDropDownBtn.addActionListener(new MenuPageBtnEventHandler());
		orderBtn.addActionListener(new OrderBtnEvenHandler(userRepo));
		orderViewBtn.addActionListener(new OrderViewBtnEventHandler());

		/* 새로운 페이지 이동 */
		logoBtn.addActionListener(this);
		signUpBtn1.addActionListener(this);
		signUpBtn2.addActionListener(this);
		menuDropDownBtn.addActionListener(this);
		orderBtn.addActionListener(this);
		orderViewBtn.addActionListener(this);
		myPageBtn.addActionListener(this);
		
		/* shutdown hook */
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				userRepo.readFromFile();
				
			}
			@Override
			public void windowClosing(WindowEvent e) {
				userRepo.storeToFile();
			}
		});

		setVisible(true);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void signInBtnAddMouseListener(JButton signInBtn) {
		signInBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String phoneNum = phoneTextField.getSelectedText().replaceAll("\\s+", "");
			}
		});
	}

	private void createTopMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("File");
		JMenuItem menuItemExit = new JMenuItem("Exit");
		
		menuFile.add(menuItemExit);
		
		menuBar.add(menuFile);
	}

	private JToolBar createNavBar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		
		//logo메뉴버튼
		ImageIcon icon = new ImageIcon(getClass().getResource("../images/burger.png"));
		icon = new ImageIcon(icon.getImage()
								.getScaledInstance(100, 50, Image.SCALE_SMOOTH));

		logoBtn = new JButton(icon);
		logoBtn.setPreferredSize(new Dimension(100, 50));
		logoBtn.setName(INIT_PAGE);

		JPanel panel = new JPanel();
        panel.add(logoBtn); //add button to panel

        toolbar.add(panel);//add panel to toolbar
		toolbar.add(new JSeparator());

		//menu dropdown
		menuDropDownBtn = createDropDownButton();
		menuDropDownBtn.setPreferredSize(new Dimension(70, 50));
		menuDropDownBtn.setName(FOOD_MENU_PAGE);
		
		panel = new JPanel();
		panel.add(menuDropDownBtn);

		toolbar.add(panel);
		toolbar.add(new JSeparator());

		//order view
		icon = new ImageIcon(getClass().getResource("../images/orderView.png"));
		icon = new ImageIcon(icon.getImage()
								.getScaledInstance(65, 45, Image.SCALE_SMOOTH));
		orderViewBtn = new JButton(icon);
		orderViewBtn.setPreferredSize(new Dimension(100, 50));
		orderViewBtn.setName(ORDER_VIEW_PAGE);

		panel = new JPanel();
        panel.add(orderViewBtn); //add button to panel

		toolbar.add(panel);
		toolbar.add(new JSeparator());
		
        //mypage
		icon = new ImageIcon(getClass().getResource("../images/mypage.png"));
		icon = new ImageIcon(icon.getImage()
								.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		myPageBtn = new JButton(icon);
		myPageBtn.setPreferredSize(new Dimension(100, 50));
		myPageBtn.setName(MY_PAGE);

		panel = new JPanel();
        panel.add(myPageBtn); //add button to panel

		toolbar.add(panel);

		return toolbar;
	}

	private JButton createDropDownButton() {
		JPopupMenu popupMenu = createDropDownMenu();
		
		ImageIcon icon = new ImageIcon(getClass().getResource("../images/menu2.png"));
		icon = new ImageIcon(icon.getImage()
								.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		
		JButton menuDropDownBtn = DropDownButtonFactory.createDropDownButton(icon, popupMenu);
//		menuDropDownBtn.addActionListener(this);
		
		return menuDropDownBtn;
	}

	private JPopupMenu createDropDownMenu() {
		JPopupMenu popupMenu = new JPopupMenu();

		List<Food> foodMenu = userRepo.getFoodMenu();
		Iterator<Food> itr = foodMenu.iterator();
		Food food = null;
		while(itr.hasNext()) {
			food = itr.next();
			JMenuItem menuItem = new JMenuItem(food.toString());
			popupMenu.add(menuItem);
			menuItem.addActionListener(new MenuItemEventHandler());
		}
		
		return popupMenu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		System.out.println(source);
		if (source instanceof JButton) {
			String name = ((JButton) e.getSource()).getName();
			switch(name) {
				case INIT_PAGE: createHomePage(); break;
				case FOOD_MENU_PAGE: createMenuPage(); break;
				case ORDER_VIEW_PAGE: createOrderViewPage(); break;
				case MY_PAGE: createMyPage(); break;
				case ORDER_PAGE: createOrderPage(); break;
				case SIGN_UP_PAGE: createSignUpPage(); break;
				default:
					break;
			}
		}
	}

	private void createHomePage() {
		System.out.println("홈페이지로 이동합니다.");
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, INIT_PAGE);
	}
	private void createMenuPage() {
		System.out.println("음식 메뉴페이지로 이동합니다."); 
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, FOOD_MENU_PAGE);
	}
	private void createOrderViewPage() {
		System.out.println("주문 조회페이지로 이동합니다."); 
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, ORDER_VIEW_PAGE);
	}
	private void createMyPage() {
		System.out.println("마이페이지로 이동합니다."); 
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, MY_PAGE);
	}
	private void createOrderPage() {
		System.out.println("주문 페이지로 이동합니다."); 
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, ORDER_PAGE);
	}
	private void createSignUpPage() {
		System.out.println("회원가입 페이지로 이동합니다."); 
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, SIGN_UP_PAGE);
	}

	public JTextField getPhoneTextField() { return phoneTextField; }
	public void setPhoneTextField(JTextField phoneTextField) { this.phoneTextField = phoneTextField; }
	private JSplitPane splitPane1, splitPane2, splitPane3;
	public JButton getLogoBtn() { return logoBtn; } 
	public void setLogoBtn(JButton logoBtn) { this.logoBtn = logoBtn; } 
	public JButton getMenuDropDownBtn() { return menuDropDownBtn; } 
	public void setMenuDropDownBtn(JButton menuDropDownBtn) { this.menuDropDownBtn = menuDropDownBtn; } 
	public JButton getOrderViewBtn() { return orderViewBtn; } 
	public void setOrderViewBtn(JButton orderViewBtn) { this.orderViewBtn = orderViewBtn; } 
	public JButton getSignInBtn1() { return signInBtn1; } 
	public void setSignInBtn1(JButton signInBtn1) { this.signInBtn1 = signInBtn1; } 
	public JButton getSignInBtn2() { return signInBtn2; } 
	public void setSignInBtn2(JButton signInBtn2) { this.signInBtn2 = signInBtn2; } 
	public JButton getSignUpBtn1() { return signUpBtn1; } 
	public void setSignUpBtn1(JButton signUpBtn1) { this.signUpBtn1 = signUpBtn1; } 
	public JButton getSignUpBtn2() { return signUpBtn2; } 
	public void setSignUpBtn2(JButton signUpBtn2) { this.signUpBtn2 = signUpBtn2; } 
	public JButton getLogOffBtn1() { return logOffBtn1; } 
	public void setLogOffBtn1(JButton logOffBtn1) { this.logOffBtn1 = logOffBtn1; } 
	public JButton getLogOffBtn2() { return logOffBtn2; } 
	public void setLogOffBtn2(JButton logOffBtn2) { this.logOffBtn2 = logOffBtn2; } 
	public JButton getOrderBtn() { return orderBtn; } 
	public void setOrderBtn(JButton orderBtn) { this.orderBtn = orderBtn; }

}
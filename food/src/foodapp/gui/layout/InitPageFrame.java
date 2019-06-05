package foodapp.gui.layout;

import static foodapp.dao.Constants.ADMIN_PAGE;
import static foodapp.dao.Constants.FOOD_MENU_PAGE;
import static foodapp.dao.Constants.INIT_PAGE;
import static foodapp.dao.Constants.ORDER_VIEW_PAGE;
import static foodapp.dao.Constants.SIGN_UP_PAGE;
import static foodapp.dao.Constants.WINDOW_HEIGHT;
import static foodapp.dao.Constants.WINDOW_WIDTH;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import foodapp.dao.UserRepository;
import foodapp.gui.event.SignInEventHandler;
import foodapp.gui.event.SignOffEventHandler;
import foodapp.model.vo.Admin;
import foodapp.model.vo.Food;
import foodapp.model.vo.FoodMenu;
import foodapp.model.vo.User;


public class InitPageFrame extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;

	private JSplitPane mainSplitPane1, mainSplitPane2, mainSplitPane3;
	private JSplitPane subSplitPane1, subSplitPane2;

	private JPanel cards;
	private JPanel card1, card2, card3, card4;

	private JPanel menuCards;
	private JPanel noodleCard, soupCard, riceCard;
	
	private JPanel topPanel, bottomPanel, rightPanel, leftPanel, subPanel1, subPanel2, centerPanel;
	
	private JButton logoBtn, foodMenuBtn, adminPageBtn, orderViewBtn;
	private JButton signInBtn1, signInBtn2, signUpBtn1, signUpBtn2;
	private JButton logOffBtn1, logOffBtn2;
	private JButton orderBtn;
	
	private JTextArea popularMenuTextArea;
	private JPanel popularMenuPanel;

	private JPanel orderSelectionPanel;
	
	private JLabel menuCategoryLabel, menuChoiceLabel, menuQtyLabel;
	private JTextField menuCategoryTxt;
	private JTextField subMenuTxt;
	private JToggleButton payCardBtn, payCashBtn;
	private ButtonGroup payMethodBtnGrp;
	
	private JLabel addMenuLabel;
	private JButton addMenuBtn;
	
	private JPanel menuCategoryPanel, menuChoicePanel, menuQtyPanel, payMethodPanel, addMenuPanel;
	
	private JButton noodleBtn, soupBtn, riceBtn;
	
	private DefaultTableModel modelN, modelS, modelR;
	
	private JComboBox<Integer> menuQtyComboBox;
	private JTextField phoneTextField;
	private JPasswordField passwordField;
	
	private JTable menuNoodleTable;
	private JTable menuSoupTable;
	private JTable menuRiceTable;
	private JScrollPane scrollNoodlePane;
	private JScrollPane scrollSoupPane;
	private JScrollPane scrollRicePane;
	
	private UserRepository userRepo = null;
	
	private Map<Food, Integer> tempOrderList;

	/* CONSTRUCTOR */
	public InitPageFrame(UserRepository userRepo) throws Exception {
		this.userRepo = userRepo;
		initialize();
		setPopularMenuList();
	}

	private void initialize() throws Exception {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocationRelativeTo(null); //center window
		getContentPane().setLayout(new BorderLayout());

		//시스템 메뉴 File-exit 생성
		createTopMenuBar();

		/* 첫번째 JSplitPane */
		createFirstTop();
		createFirstBottom();
		createFirstSP();

		/* 두번째 JSplitPane */
		createSecondTop();
		createSecondBottom();
		createSecondSP();

		/* 세번째 JSplitPane */
		createThirdTop();
		createThirdBottom();
		createThirdSP();

		/* 생성한 JSplitPane의 border 설정 */
		invokeSplitPane();

		/* CardLayout 만들기 */
		updateCards();

		/* window closing */
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {
				User user = null;
				if (userRepo.getPhone() != null) {
					user = (User)userRepo.getUserByPhone(userRepo.getPhone());
					if (user == null)
						return;
					if(user.isOrdering())
						user.setOrdering(false);

					user.setLogged(false);
				}
				userRepo.storeToFile();
			}
		});

		setVisible(true);
		setResizable(false);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void createFirstSP() {
		mainSplitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mainSplitPane1.setDividerLocation(300 + mainSplitPane1.getInsets().top);
		mainSplitPane1.setDividerSize(1);

		mainSplitPane1.setTopComponent(topPanel);
		mainSplitPane1.setBottomComponent(bottomPanel);
		mainSplitPane1.setEnabled(false);
	}

	private void createFirstTop() {
		createFirstTopLeft();
		createFirstTopCenter();
		createFirstTopRight();

		topPanel = new JPanel(new GridLayout(1,3));
		topPanel.add(leftPanel);
		topPanel.add(centerPanel);
		topPanel.add(rightPanel);
	}
	
	private void createFirstTopLeft() {
		leftPanel = new JPanel(new GridLayout(3,2));
		ImageIcon icon = null;
		icon = new ImageIcon(getClass().getResource("../images/noodle_char.jpg"));
		icon = new ImageIcon(icon.getImage().getScaledInstance(130, 100, Image.SCALE_SMOOTH));
		leftPanel.add(new JLabel(icon));
		icon = new ImageIcon(getClass().getResource("../images/noodle.png"));
		icon = new ImageIcon(icon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH));
		noodleBtn = new JButton(icon); noodleBtn.setName("NOODLE");
		leftPanel.add(noodleBtn);

		icon = new ImageIcon(getClass().getResource("../images/soup_char.jpg"));
		icon = new ImageIcon(icon.getImage().getScaledInstance(130, 100, Image.SCALE_SMOOTH));
		leftPanel.add(new JLabel(icon));
		icon = new ImageIcon(getClass().getResource("../images/soup.png"));
		icon = new ImageIcon(icon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH));
		soupBtn = new JButton(icon); soupBtn.setName("SOUP");
		leftPanel.add(soupBtn);

		icon = new ImageIcon(getClass().getResource("../images/rice_char.jpg"));
		icon = new ImageIcon(icon.getImage().getScaledInstance(130, 100, Image.SCALE_SMOOTH));
		leftPanel.add(new JLabel(icon));
		icon = new ImageIcon(getClass().getResource("../images/rice.png"));
		icon = new ImageIcon(icon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH));
		riceBtn = new JButton(icon); riceBtn.setName("RICE");
		leftPanel.add(riceBtn);
	}

	private void createFirstTopCenter() {
		CardLayout layout = new CardLayout();
		menuCards = new JPanel(layout);

		noodleCard = new JPanel(new BorderLayout());
		soupCard = new JPanel(new BorderLayout());
		riceCard = new JPanel(new BorderLayout());
		
		Map<String, DefaultTableModel> tableModels = constructTableModels();
		modelN = tableModels.get("NOODLE");
		modelS = tableModels.get("SOUP");
		modelR = tableModels.get("RICE");
		
		menuNoodleTable = new JTable(modelN);
		menuNoodleTable.setName("NOODLE_TABLE");
		menuSoupTable = new JTable(modelS);
		menuSoupTable.setName("SOUP_TABLE");
		menuRiceTable = new JTable(modelR);
		menuRiceTable.setName("RICE_TABLE");

		menuNoodleTable.setAutoCreateRowSorter(true);
		menuSoupTable.setAutoCreateRowSorter(true);
		menuRiceTable.setAutoCreateRowSorter(true);

		menuNoodleTable.addMouseListener(this);
		menuSoupTable.addMouseListener(this);
		menuRiceTable.addMouseListener(this);

		scrollNoodlePane = new JScrollPane(menuNoodleTable);
		scrollSoupPane = new JScrollPane(menuSoupTable);
		scrollRicePane = new JScrollPane(menuRiceTable);
		
		noodleCard.add(scrollNoodlePane);
		soupCard.add(scrollSoupPane);
		riceCard.add(scrollRicePane);

		menuCards.add(noodleCard, "NOODLE");
		menuCards.add(soupCard,"SOUP");
		menuCards.add(riceCard, "RICE");
		
		
		orderSelectionPanel = new JPanel(new GridLayout(5,1));

		menuCategoryLabel = new JLabel("메뉴 카테고리");
		menuCategoryTxt = new JTextField("", 10);
		menuCategoryTxt.setEditable(false);
		
		menuChoiceLabel = new JLabel("선택 메뉴");
		subMenuTxt = new JTextField(10);
		subMenuTxt.setEditable(false);

		menuQtyLabel = new JLabel("수 량");
		ComboBoxModel<Integer> comboBoxModel = 
				new DefaultComboBoxModel<Integer>(new Integer[] {1,2,3,4,5});
		menuQtyComboBox = new JComboBox<Integer>(comboBoxModel);
		
		addMenuLabel = new JLabel("메뉴 추가");
		addMenuBtn = new JButton("PUSH 추가");
		addMenuBtn.setName("ADD_FOOD");

		payCardBtn = new JToggleButton("카드 주문");
		payCardBtn.setName("CARD");
		payCardBtn.setBackground(Color.WHITE);
		payCashBtn = new JToggleButton("현금 주문");
		payCashBtn.setName("CASH");
		payCashBtn.setBackground(Color.WHITE);

		payMethodBtnGrp = new ButtonGroup();
		payMethodBtnGrp.add(payCardBtn);
		payMethodBtnGrp.add(payCashBtn);
		

		menuCategoryPanel = new JPanel(new GridLayout(1,2));
		menuChoicePanel = new JPanel(new GridLayout(1,2));
		menuQtyPanel = new JPanel(new GridLayout(1,2));
		payMethodPanel = new JPanel(new GridLayout(1,2));
		addMenuPanel = new JPanel(new GridLayout(1,2));

		menuCategoryPanel.add(menuCategoryLabel); menuCategoryPanel.add(menuCategoryTxt);
		menuChoicePanel.add(menuChoiceLabel); menuChoicePanel.add(subMenuTxt);
		menuQtyPanel.add(menuQtyLabel); menuQtyPanel.add(menuQtyComboBox);
		payMethodPanel.add(payCardBtn); payMethodPanel.add(payCashBtn);
		addMenuPanel.add(addMenuLabel); addMenuPanel.add(addMenuBtn);
		
		orderSelectionPanel.add(menuCategoryPanel);
		orderSelectionPanel.add(menuChoicePanel);
		orderSelectionPanel.add(menuQtyPanel);
		orderSelectionPanel.add(addMenuPanel);
		orderSelectionPanel.add(payMethodPanel);
		
		subSplitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		subSplitPane1.setTopComponent(menuCards);
		subSplitPane1.setBottomComponent(orderSelectionPanel);
		subSplitPane1.setDividerLocation(210 + subSplitPane1.getInsets().top);
		subSplitPane1.setDividerSize(1);
		subSplitPane1.setEnabled(false);

		centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(subSplitPane1, BorderLayout.CENTER);


		noodleBtn.addMouseListener(this);
		soupBtn.addMouseListener(this);
		riceBtn.addMouseListener(this);
		
		addMenuBtn.addMouseListener(this);

		payCardBtn.addMouseListener(this);
		payCashBtn.addMouseListener(this);
	}

	private void createFirstTopRight() {
		//버튼, 텍스트 필드 초기화
		phoneTextField = new JTextField("", 11); //핸드폰 11자리
		passwordField = new JPasswordField(11);
		signInBtn1 = new JButton("로그인");
		logOffBtn1 = new JButton("로그아웃");
		signUpBtn1 = new JButton("회원가입");
		signUpBtn1.setName(SIGN_UP_PAGE);

		rightPanel = new JPanel(new GridLayout(4,1));
		subPanel1 = new JPanel(new GridLayout(2,1)); 
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p1.add(new JLabel("핸드폰 번호"));
		p1.add(phoneTextField);
		p2.add(new JLabel("비밀번호"));
		p2.add(passwordField);
		subPanel1.add(p1);
		subPanel1.add(p2);
		subPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		subPanel2.add(signInBtn1);
		subPanel2.add(signUpBtn1);
		subPanel2.add(logOffBtn1);
		rightPanel.add(new JLabel());
		rightPanel.add(subPanel1);
		rightPanel.add(subPanel2);

		signInBtn1.addMouseListener(new SignInEventHandler(phoneTextField, passwordField, userRepo));
		logOffBtn1.addMouseListener(new SignOffEventHandler(phoneTextField, passwordField, userRepo));
		signUpBtn1.addMouseListener(this);
	}

	private void createFirstBottom() {
		popularMenuTextArea = new JTextArea(200, 300);
		popularMenuTextArea.setEditable(false);
		popularMenuPanel = new JPanel(new BorderLayout());
		popularMenuPanel.add(popularMenuTextArea);

		orderBtn = new JButton("주문 하기");
		orderBtn.setName("ORDER");
		
		subSplitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		subSplitPane2.setTopComponent(popularMenuPanel);
		subSplitPane2.setBottomComponent(orderBtn);

		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(subSplitPane2);

		orderBtn.addMouseListener(this);
	}
	
	private void createSecondSP() {
		mainSplitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mainSplitPane2.setTopComponent(topPanel);
		mainSplitPane2.setBottomComponent(bottomPanel);
		mainSplitPane2.setEnabled(false);

	}
	
	private void createSecondTop() {
		/* 네비게이션 메뉴 */
		JToolBar navBar = this.createNavBar();

		topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(navBar);

		logoBtn.addMouseListener(this);
		foodMenuBtn.addMouseListener(this);
		orderViewBtn.addMouseListener(this);
		adminPageBtn.addMouseListener(this);
		
	}

	private void createSecondBottom() {
		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(mainSplitPane1);
	}
	
	private void createThirdSP() {
		mainSplitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mainSplitPane3.setDividerLocation(30 + mainSplitPane3.getInsets().top);
		mainSplitPane3.setDividerSize(1);
		mainSplitPane3.setTopComponent(topPanel);
		mainSplitPane3.setBottomComponent(bottomPanel);
		mainSplitPane3.setEnabled(false);
	}

	private void createThirdTop() {
		signInBtn2 = new JButton("로그인");
		logOffBtn2 = new JButton("로그아웃");
		signUpBtn2 = new JButton("회원가입");
		signUpBtn2.setName(SIGN_UP_PAGE);

		topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		topPanel.add(signInBtn2);
		topPanel.add(signUpBtn2);
		topPanel.add(logOffBtn2);

		signInBtn2.addMouseListener(new SignInEventHandler(phoneTextField, passwordField, userRepo));
		logOffBtn2.addMouseListener(new SignOffEventHandler(phoneTextField, passwordField, userRepo));
		signUpBtn2.addMouseListener(this);
	}

	private void createThirdBottom() {
		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(mainSplitPane2);
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
//		menuDropDownBtn = createDropDownButton();
		icon = new ImageIcon(getClass().getResource("../images/menu2.png"));
		icon = new ImageIcon(icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		foodMenuBtn = new JButton(icon);
		
		foodMenuBtn.setPreferredSize(new Dimension(70, 50));
		foodMenuBtn.setName(FOOD_MENU_PAGE);
		
		panel = new JPanel();
		panel.add(foodMenuBtn);

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
		adminPageBtn = new JButton(icon);
		adminPageBtn.setPreferredSize(new Dimension(100, 50));
		adminPageBtn.setName(ADMIN_PAGE);

		panel = new JPanel();
        panel.add(adminPageBtn); //add button to panel

		toolbar.add(panel);

		return toolbar;
	}

	private Map<String, DefaultTableModel> constructTableModels() {
		Map<String, DefaultTableModel> map = new TreeMap<String, DefaultTableModel>();
		
		FoodMenu menu = userRepo.getFoodMenu();
		List<Food> foodMenuList = null;
		Iterator<Food> itr = null;
		if(menu!= null)
			foodMenuList = menu.getFoodMenuList();

		if(foodMenuList != null) {
			Collections.sort(foodMenuList, (i,j)->{
				return i.getMenuCategory().compareTo(j.getMenuCategory()) == 0 ? 
							i.getMenuNo() - j.getMenuNo(): i.getMenuCategory().compareTo(j.getMenuCategory());
			});

			itr = foodMenuList.iterator();
		}

		Food food = null;
		String[] colNames = {"카테고리", "메뉴번호", "메뉴이름", "가격"};
        modelN = new DefaultTableModel(colNames, 0);
        modelS = new DefaultTableModel(colNames, 0);
        modelR = new DefaultTableModel(colNames, 0);

		String[][] tempNoodleList = new String[foodMenuList ==null? 0: foodMenuList.size()][colNames.length];
		String[][] tempSoupList = new String[foodMenuList ==null? 0: foodMenuList.size()][colNames.length];
		String[][] tempRiceList = new String[foodMenuList ==null? 0: foodMenuList.size()][colNames.length];

		int countN =0, countS=0, countR=0;

		while(itr!= null && itr.hasNext()) {
			food = itr.next();

			String[] temp = new String[] { food.getMenuCategory(), 
					String.valueOf(food.getMenuNo()), 
					food.getMenuName(), 
					food.toCurrency(food.getMenuPrice()) };
			switch(food.getMenuCategory()) {
				case "NOODLE": tempNoodleList[countN++] = temp;break;
				case "SOUP": tempSoupList[countS++] = temp; break;
				case "RICE": tempRiceList[countR++] = temp; break;
			}
		}
		
		String[][] noodleList = new String[countN][colNames.length];
		String[][] soupList = new String[countS][colNames.length];
		String[][] riceList = new String[countR][colNames.length];
		
		for(int i =0; i<noodleList.length; i++) {
			for(int j =0; j<noodleList[i].length; j++)
				noodleList[i][j] = tempNoodleList[i][j];
		}
		for(int i =0; i<soupList.length; i++) {
			for(int j =0; j<soupList[i].length; j++)
				soupList[i][j] = tempSoupList[i][j];
		}
		for(int i =0; i<riceList.length; i++) {
			for(int j =0; j<riceList[i].length; j++)
				riceList[i][j] = tempRiceList[i][j];
		}
		
		for(int i =0;  i<noodleList.length; i++)  modelN.addRow(noodleList[i]);
		for(int i =0;  i<soupList.length; i++)  modelS.addRow(soupList[i]);
		for(int i =0;  i<riceList.length; i++)  modelR.addRow(riceList[i]);
		
		map.put("NOODLE", modelN);
		map.put("SOUP", modelS);
		map.put("RICE", modelR);

		return map;
	}

	private void invokeSplitPane() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
				mainSplitPane2.setDividerLocation(53 + mainSplitPane2.getInsets().top);
				mainSplitPane2.setDividerSize(1);

				subSplitPane2.setDividerLocation(93+ subSplitPane2.getInsets().top);
				subSplitPane2.setEnabled(false);
				subSplitPane2.setDividerSize(1);
            }
        });
    }

	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		Object source = e.getSource();
		if (source instanceof JButton) {
			String name = ((JButton) e.getSource()).getName();
			switch(name) {
				case INIT_PAGE: createHomePage(); break;
				case FOOD_MENU_PAGE: createFoodMenuPage(); break;
				case ORDER_VIEW_PAGE: createOrderViewPage(); break;
				case ADMIN_PAGE: createAdminPage(); break;
				case SIGN_UP_PAGE: createSignUpPage(); break;
				case "NOODLE": showNoodleMenu(); break;
				case "SOUP": showSoupMenu(); break;
				case "RICE": showRiceMenu(); break;
				case "ADD_FOOD": saveOrderList(name); break;
				case "CARD": 
					payCardBtn.setText("카드 주문 (선택됨)");
					payCashBtn.setText("현금 주문");
					break;
				case "CASH": 
					payCashBtn.setText("현금 주문 (선택됨)");
					payCardBtn.setText("카드 주문");
					break;
				case "ORDER": completeOrder(); break;
				default:
					break;
			}
		}
		else if(source instanceof JTable) {
			DefaultTableModel model = null;
			JTable table = (JTable)e.getSource();
			String name = table.getName();
			switch(name) {
				case "NOODLE_TABLE": model = (DefaultTableModel)menuNoodleTable.getModel(); break;
				case "SOUP_TABLE": model = (DefaultTableModel)menuSoupTable.getModel(); break;
				case "RICE_TABLE": model = (DefaultTableModel)menuRiceTable.getModel(); break;
				default:
					break;
			}

			int row = table.getSelectedRow();
			System.out.println(model.getValueAt(row, 1));
			System.out.println((String)model.getValueAt(row, 2));

			Object o1 = model.getValueAt(row, 1);
			Object o2 = model.getValueAt(row, 2);
			if(o1 == null || o2 == null) return;
			this.subMenuTxt.setText(o1.toString() + ". " + o2.toString());
		}
	}

	public void completeOrder() {
		if(this.phoneTextField.getText() == "") {
			JOptionPane.showMessageDialog(null, "로그인이 필요합니다.", "로그인 확인", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(!payCardBtn.isSelected() && !payCashBtn.isSelected()) {
			System.out.println("결재수단을 선택해 주세요.");
			return;
		}

		int result = JOptionPane.showConfirmDialog(null, "주문을 완료하시겠습니까?", "주문 확인",
				JOptionPane.OK_CANCEL_OPTION);

		if(result!= JOptionPane.OK_OPTION) {
			System.out.println("주문을 취소합니다.");
			return;
		}

		String payMethod = this.payCardBtn.isSelected()? "CARD" : "CASH";

		User user = userRepo.getUserByPhone(this.phoneTextField.getText());
		if(user == null)
			return;
		if(!user.isOrdering())
			return;

		Map<Food, Integer> orderList = tempOrderList;

		User admin = userRepo.getAdmin();

		Map<Food, Integer> salesResult = ((Admin)admin).getSalesResult();
		if (salesResult == null)
			((Admin) admin).setSalesResult(new TreeMap<Food, Integer>());

		Food food = null;
		int qty  =0;
		for(Map.Entry<Food, Integer> entry : orderList.entrySet()) {
			food = entry.getKey();
			qty = entry.getValue();

			for(Map.Entry<Food, Integer> e: salesResult.entrySet()) {
				System.out.println(e.getKey());
				System.out.println(e.getValue());
				System.out.println(food.equals(e.getKey()));
			}
			if(salesResult.get(food) != null) {
				System.out.println(food);
				System.out.println(salesResult.get(food));
				salesResult.put(food, salesResult.get(food) + qty);
				System.out.println(salesResult.get(food));
			}
			else
				salesResult.put(food, qty);
		}
		((Admin)admin).setSalesResult(salesResult);
		user.setOrderList(tempOrderList);
		user.setRecentPayMethod(payMethod);
		user.setOrdering(false);
		user.setOrderCreated(new GregorianCalendar());
		userRepo.showUsers();
		setPopularMenuList();

		JOptionPane.showMessageDialog(null, "주문이 완료 되었습니다.", "주문결제 완료 확인", JOptionPane.WARNING_MESSAGE);
	}

	public void saveOrderList(String name) {
		if(this.phoneTextField.getText() == ""
				|| (this.phoneTextField.isEditable()
						&& this.passwordField.isEditable())) {
			JOptionPane.showMessageDialog(null, "로그인이 필요합니다.", "로그인 확인", JOptionPane.WARNING_MESSAGE);
			return;
		}

		User user = userRepo.getUserByPhone(this.phoneTextField.getText());

		if (user == null) {
			JOptionPane.showMessageDialog(null, "핸드폰정보 유저 없음", "로그인 확인", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if(!name.equals("ADD_FOOD")) {
			JOptionPane.showMessageDialog(null, "잘못된 메뉴입니다", "프로그램 버그 확인", JOptionPane.WARNING_MESSAGE);
			return;
		}


		if(!user.isOrdering()) {
//			user.setOrderList(new TreeMap<Food, Integer>());
			this.tempOrderList = new TreeMap<Food, Integer>();
			user.setOrdering(true);
		}

		TableModel model = null;
		int row = 0;
		if(this.menuCategoryTxt.getText().equals(""))
			this.menuCategoryTxt.setText("면 메뉴");
		
		switch(this.menuCategoryTxt.getText().charAt(0)) {
			case '면':
				model = (DefaultTableModel)menuNoodleTable.getModel();
				row = menuNoodleTable.getSelectedRow();
				break;
			case '탕': 
				model = (DefaultTableModel)menuSoupTable.getModel();
				row = menuSoupTable.getSelectedRow();
				break;
			case '밥':
				model = (DefaultTableModel)menuRiceTable.getModel();
				row = menuRiceTable.getSelectedRow();
				break;
		}

		String menuCategory = (String)model.getValueAt(row, 0);
		int menuNo = Integer.valueOf((String)model.getValueAt(row, 1));
		String menuName=  (String)model.getValueAt(row, 2);
		int menuPrice = 0;
		String temp = ((String)model.getValueAt(row, 3)).substring(1);
		menuPrice = Integer.valueOf(temp.replace(",", ""));

		Food food = new Food(menuCategory, menuNo, menuName, menuPrice);
		
		tempOrderList.put(food, (int)this.menuQtyComboBox.getSelectedItem());

		JOptionPane.showMessageDialog(null, "주문이 추가되었습니다.", "주문 추가 확인", JOptionPane.WARNING_MESSAGE);
		return;
	}

	private void showNoodleMenu() {
		CardLayout cl = (CardLayout)(menuCards.getLayout());
        cl.show(menuCards, "NOODLE");
        this.menuCategoryTxt.setText("면 메뉴");
	}

	private void showSoupMenu() {
		CardLayout cl = (CardLayout)(menuCards.getLayout());
        cl.show(menuCards, "SOUP");
        this.menuCategoryTxt.setText("탕 메뉴");
	}

	private void showRiceMenu() {
		CardLayout cl = (CardLayout)(menuCards.getLayout());
        cl.show(menuCards, "RICE");
        this.menuCategoryTxt.setText("밥 메뉴");
	}

	private void createAdminPage() {
		if(phoneTextField.getText() == null
				|| !phoneTextField.getText().equals("admin")) {
			JOptionPane.showMessageDialog(null, "관리자로 먼저 로그인 해주세요.", "관리자 권한 에러", JOptionPane.WARNING_MESSAGE);
			return;
		}

		JFrame frame = new AdminPageFrame(modelN, modelS, modelR, userRepo);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) { 
			}
		});

		frame.setSize(500, 500);
		frame.setLocation(this.getX() + 50 , this.getY() + 90);
		frame.setLocationRelativeTo(null);

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true); 
	}

	private void createHomePage() {
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, INIT_PAGE);
	}

	private void updateCards() {
		CardLayout cl = null;
		if(cards == null
				|| !(cards.getLayout() instanceof CardLayout)) 
			cl = new CardLayout();
		else
			cl = (CardLayout)cards.getLayout();


		cards = new JPanel(cl);

		card1 = new JPanel(new BorderLayout());
		card2 = new JPanel(new BorderLayout());
		card3 = new JPanel(new BorderLayout());
		card4 = new JPanel(new BorderLayout());

		card1.add(mainSplitPane3);
		card2.add(new FoodMenuPageFrame(cl, cards, userRepo));
		card3.add(new OrderViewPageFrame(cl, cards, phoneTextField, userRepo));
		card4.add(new SignUpPageFrame(cl, cards, userRepo));

		cards.add(card1, INIT_PAGE);
		cards.add(card2, FOOD_MENU_PAGE);
		cards.add(card3, ORDER_VIEW_PAGE);
		cards.add(card4, SIGN_UP_PAGE);

		add(cards);

		cards.revalidate();
	}

	private void createFoodMenuPage() {
		CardLayout cl = (CardLayout)(cards.getLayout());
		updateCards();

        cl.show(cards, FOOD_MENU_PAGE);
	}

	private void createOrderViewPage() {
		CardLayout cl = (CardLayout)(cards.getLayout());
		updateCards();
        cl.show(cards, ORDER_VIEW_PAGE);
	}
	
	private void createSignUpPage() {
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, SIGN_UP_PAGE);
	}

	private void setPopularMenuList() {
		Map<Food, Integer> salesResult = (TreeMap<Food, Integer>)((Admin)userRepo.getAdmin()).getSalesResult();				
		if (salesResult == null)
			return;

		List<Map.Entry<Integer, Food>> sortedSales = new ArrayList<Map.Entry<Integer, Food>>();

		Map.Entry<Integer, Food> newEntry = null;
		for(Map.Entry<Food, Integer> entry : salesResult.entrySet()) {
			newEntry = new AbstractMap.SimpleEntry<Integer, Food>(entry.getValue(), entry.getKey());
			sortedSales.add(newEntry);
		}
		
		Collections.sort(sortedSales, (i,j)->{
			return j.getKey() - i.getKey();
		});

		int count = 0;
		int qty = 0;
		String msg = "    ★  인기 메뉴\n";
		Food food = null;
		Iterator<Map.Entry<Integer, Food>> itr = sortedSales.iterator();
		this.popularMenuTextArea.setText("");

		while(itr.hasNext() && count++ < 5) {
			newEntry = itr.next();
			food = newEntry.getValue();
			qty = newEntry.getKey();
			msg += "    " + count+ "등 메뉴:   " + food.toString() + " - - - 총 " + qty + " 개 팔렸어요.";
			if(count <5) msg+="\n";
		}
		popularMenuTextArea.setText(msg);
		Font font = new Font("맑은고딕", Font.BOLD, 11);
        popularMenuTextArea.setFont(font);
        popularMenuTextArea.setForeground(Color.BLUE);
	}

}
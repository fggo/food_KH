package com.kh.food.gui;
		
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import com.kh.food.view.MainMenu;

public class LoginPageFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 900;
	private static final int HEIGHT = 900;
	
	private JSplitPane splitPane1, splitPane2, splitPane3;
	private JPanel topPanel, bottomPanel, rightPanel, leftPanel, subPanel1, subPanel2;
	
	private JButton logoBtn, menuDropDownBtn, myPageBtn, orderViewBtn;
	private JButton signInBtn1, signInBtn2, signUpBtn1, signUpBtn2, orderBtn;
	
	private JTextField phoneInput;

	public LoginPageFrame(String title, MainMenu menu) throws HeadlessException {
		super(title);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		
		this.createMenuBar();
		
		getContentPane().setLayout(new BorderLayout());

		splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane1.setDividerLocation(300 + splitPane1.getInsets().top);

		phoneInput = new JTextField("", 11);
		signInBtn1 = new JButton("로그인");
		signUpBtn1 = new JButton("회원가입");
		orderBtn = new JButton("주문하기");

		leftPanel = new JPanel();
		rightPanel = new JPanel(new GridLayout(4,1));
		subPanel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
		subPanel1.add(new JLabel("핸드폰 번호"));
		subPanel1.add(phoneInput);
		subPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		subPanel2.add(signInBtn1);
		subPanel2.add(signUpBtn1);
		rightPanel.add(new JLabel());
		rightPanel.add(subPanel1);
		rightPanel.add(subPanel2);

		topPanel = new JPanel(new GridLayout(1,2));
		topPanel.add(leftPanel);
		topPanel.add(rightPanel);
		bottomPanel = new JPanel(new GridLayout(2,1));
		bottomPanel.add(new JPanel());
		bottomPanel.add(orderBtn);
		splitPane1.setTopComponent(topPanel);
		splitPane1.setBottomComponent(bottomPanel);
		splitPane1.setEnabled(false);

		
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


		JPanel topMostPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		signInBtn2 = new JButton("로그인");
		signUpBtn2 = new JButton("회원가입");
		topMostPanel.add(signInBtn2);
		topMostPanel.add(signUpBtn2);
		splitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane3.setDividerLocation(30 + splitPane3.getInsets().top);

		topPanel = topMostPanel;
		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(splitPane2);
		splitPane3.setTopComponent(topPanel);
		splitPane3.setBottomComponent(bottomPanel);
		splitPane3.setEnabled(false);

		add(splitPane3);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	private JToolBar createNavBar() {

		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		
		//logo메뉴버튼
		ImageIcon icon = new ImageIcon(getClass().getResource("images/burger.png"));
		icon = new ImageIcon(icon.getImage()
								.getScaledInstance(100, 50, Image.SCALE_SMOOTH));

		logoBtn = new JButton(icon);
		logoBtn.setPreferredSize(new Dimension(100, 50));

		JPanel panel = new JPanel();
        panel.add(logoBtn); //add button to panel

        toolbar.add(panel);//add panel to toolbar
		toolbar.add(new JSeparator());

		
		//menu dropdown
		menuDropDownBtn = createDropDownButton();
		menuDropDownBtn.setPreferredSize(new Dimension(70, 50));
		
		panel = new JPanel();
		panel.add(menuDropDownBtn);

		toolbar.add(panel);
		toolbar.add(new JSeparator());


		//order view
		icon = new ImageIcon(getClass().getResource("images/orderView.gif"));
		icon = new ImageIcon(icon.getImage()
								.getScaledInstance(100, 49, Image.SCALE_SMOOTH));
		orderViewBtn = new JButton(icon);
		orderViewBtn.setPreferredSize(new Dimension(100, 50));

		panel = new JPanel();
        panel.add(orderViewBtn); //add button to panel

		toolbar.add(panel);
		toolbar.add(new JSeparator());
		
        //mypage
		icon = new ImageIcon(getClass().getResource("images/mypage.png"));
		icon = new ImageIcon(icon.getImage()
								.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		myPageBtn = new JButton(icon);
		myPageBtn.setPreferredSize(new Dimension(100, 50));

		panel = new JPanel();
        panel.add(myPageBtn); //add button to panel

		toolbar.add(panel);

//		setLayout(new FlowLayout(FlowLayout.LEFT));
//		add(toolbar);

		return toolbar;
	}

	private JButton createDropDownButton() {
		JPopupMenu popupMenu = createDropDownMenu();
		
		ImageIcon icon = new ImageIcon(getClass().getResource("images/menu2.png"));
		icon = new ImageIcon(icon.getImage()
								.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		
		JButton menuDropDownBtn = DropDownButtonFactory.createDropDownButton(icon, popupMenu);
		menuDropDownBtn.addActionListener(this);
		
		return menuDropDownBtn;
	}

	private JPopupMenu createDropDownMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		


		JMenuItem menuItemCreateSpringProject = new JMenuItem("햄버거 - 2000원");
		popupMenu.add(menuItemCreateSpringProject);
		
		JMenuItem menuItemCreateHibernateProject = new JMenuItem("우유 - 1000원");
		popupMenu.add(menuItemCreateHibernateProject);
		
		JMenuItem menuItemCreateStrutsProject = new JMenuItem("콜라 - 700원");
		popupMenu.add(menuItemCreateStrutsProject);
		
		menuItemCreateSpringProject.addActionListener(this);
		menuItemCreateHibernateProject.addActionListener(this);
		menuItemCreateStrutsProject.addActionListener(this);
		
		return popupMenu;
	}

	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("File");
		JMenuItem menuItemExit = new JMenuItem("Exit");
		
		menuFile.add(menuItemExit);
		
		menuBar.add(menuFile);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
//		System.out.println(source);
		if (source instanceof JMenuItem) {
			JMenuItem clickedMenuItem = (JMenuItem) source;
			String menuText = clickedMenuItem.getText();
			System.out.println(menuText+ "를 추가합니다.");
		} else if (source instanceof JButton) {
			System.out.println("메뉴를 선택합니다.");
		}
	}

	
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new LoginPageFrame("food", new MainMenu()).setVisible(true);
//		SwingUtilities.invokeLater(new Runnable() {
//			
//			@Override
//			public void run() {
//			}
//		});
	}

}
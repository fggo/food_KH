package foodapp.gui.layout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import foodapp.dao.UserRepository;
import foodapp.model.vo.Admin;
import foodapp.model.vo.Food;

public class AdminFrame extends JFrame implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel adminLeftPanel;
	private JSplitPane adminCenterSplitPane;
	private JPanel salesLabelPanel;
	private JLabel salesLabel;
	private JTextArea salesResultTextArea;
	private JScrollPane scrollTextArea;

	private JPanel scrollPanePanel1, scrollPanePanel2;

	private JSplitPane adminSplitPane1;
	private JSplitPane adminSplitPane2;
	private JPanel adminRightPanel;

	private JSplitPane adminSplitPane3;
	private JPanel openMenuManagePanel;
	private JButton openManuManageBtn;

	
	private JPanel backButtonPanel;
	private JButton backBtn;

	private JPanel cards;
	private JPanel cardSalesResult, cardManageMenu;


	private JPanel buttonPanel;
		
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
		
	private JButton addBtn;
	private JButton modifyBtn;
	private JButton deleteBtn;



	private DefaultTableModel tableModel;
	
	private JTable menuTable;
	private JScrollPane scrollPane;

	private JSplitPane menuSplitPane1, menuSplitPane2;
	private JPanel topPanel;
	private JPanel bottomPanel;

	private UserRepository userRepo;

	public AdminFrame(UserRepository userRepo) {
		this.userRepo = userRepo;

		initialize();
	}

	
	private void initialize(){
		adminLeftPanel = new JPanel();
		adminCenterSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		salesLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		salesLabel = new JLabel("매출 내역");
		salesLabelPanel.add(salesLabel);
		salesResultTextArea = new JTextArea(300, 400);
		salesResultTextArea.setEditable(false);

		Map<Food, Integer> salesResult = (TreeMap<Food, Integer>)((Admin)userRepo.getAdmin()).getSalesResult();				
		if (salesResult == null)
			salesResult = new TreeMap<Food, Integer>();

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
		String msg = "";
		Food food = null;
		Iterator<Map.Entry<Integer, Food>> itr = sortedSales.iterator();
		salesResultTextArea.setText("");

		while(itr.hasNext()) {
			newEntry = itr.next();
			food = newEntry.getValue();
			qty = newEntry.getKey();
			msg += "    " + food.toString() + " - - - 총 " + qty + " 개.";
			if(++count < salesResult.size()) msg+="\n";
		}
		salesResultTextArea.setText(msg);
		Font font = new Font("맑은고딕", Font.BOLD, 11);
        salesResultTextArea.setFont(font);
        salesResultTextArea.setForeground(Color.BLUE);
		scrollTextArea = new JScrollPane(salesResultTextArea,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanePanel1 = new JPanel(new BorderLayout());
		scrollPanePanel1.add(scrollTextArea);
		adminCenterSplitPane.setTopComponent(salesLabelPanel);
		adminCenterSplitPane.setBottomComponent(scrollPanePanel1);

		adminSplitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		adminSplitPane1.setLeftComponent(adminLeftPanel);
		adminSplitPane1.setRightComponent(adminCenterSplitPane);

		adminSplitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		adminRightPanel = new JPanel();
		adminSplitPane2.setLeftComponent(adminSplitPane1);
		adminSplitPane2.setRightComponent(adminRightPanel);
		
		adminSplitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		openMenuManagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		openManuManageBtn = new JButton("메뉴 관리");
		openManuManageBtn.setName("MANAGE_MENU");
		openManuManageBtn.addMouseListener(this);
		openMenuManagePanel.add(openManuManageBtn);
		adminSplitPane3.setTopComponent(openMenuManagePanel);
		adminSplitPane3.setBottomComponent(adminSplitPane2);
		
		

		buttonPanel = new JPanel(new GridLayout(1,3));
		
		panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		addBtn = new JButton("추 가");
		addBtn.setName("ADD");
		modifyBtn = new JButton("수 정");
		modifyBtn.setName("MODIFY");
		deleteBtn = new JButton("삭 제");
		deleteBtn.setName("DELETE");
		
		addBtn.addMouseListener(this);
		modifyBtn.addMouseListener(this);
		deleteBtn.addMouseListener(this);
		
		panel1.add(addBtn);
		panel2.add(modifyBtn);
		panel3.add(deleteBtn);
		
		buttonPanel.add(panel1);
		buttonPanel.add(panel2);
		buttonPanel.add(panel3);
		




		String[] colNames = {"카테고리", "메뉴번호", "메뉴이름", "가격"};
        tableModel = new DefaultTableModel(colNames, 0);

		String[][] menuTableContents = new String[userRepo.getFoodMenu().getFoodMenuList().size()][colNames.length];

		List<Food> foodMenuList = (ArrayList<Food>)userRepo.getFoodMenu().getFoodMenuList();

		Iterator<Food> it = foodMenuList.iterator();

		food = null;
		count =0;
		while(it.hasNext()) {
			food = it.next();
			
			String[] row = new String[] { food.getMenuCategory(), 
						String.valueOf(food.getMenuNo()), 
						food.getMenuName(), 
						food.toCurrency(food.getMenuPrice()),
					};
			menuTableContents[count++] = row;
		}
		
		for(int i =0;  i<menuTableContents.length; i++)
			tableModel.addRow(menuTableContents[i]);

		menuTable = new JTable(tableModel);
		menuTable.setName("MENU_TABLE");
		menuTable.setAutoCreateRowSorter(true);
		menuTable.addMouseListener(this);
		scrollPane = new JScrollPane(menuTable,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanePanel2 = new JPanel(new BorderLayout());
		scrollPanePanel2.add(scrollPane);

		menuSplitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		menuSplitPane1.setTopComponent(buttonPanel);
		menuSplitPane1.setBottomComponent(scrollPanePanel2);
		menuSplitPane1.setDividerLocation(50 + menuSplitPane1.getInsets().top);
		menuSplitPane1.setDividerSize(1);


		
		backBtn = new JButton("뒤로가기");
		backBtn.setName("SALES_RESULT");
		backBtn.addMouseListener(this);
		backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		backButtonPanel.add(backBtn);
		topPanel = backButtonPanel;
		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(menuSplitPane1);

		menuSplitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		menuSplitPane2.setTopComponent(topPanel);
		menuSplitPane2.setBottomComponent(bottomPanel);
		menuSplitPane2.setDividerLocation(30 + menuSplitPane2.getInsets().top);
		menuSplitPane2.setDividerSize(1);



		CardLayout cl = new CardLayout();
		cards = new JPanel(cl);

		cardSalesResult = new JPanel(new BorderLayout());
		cardManageMenu = new JPanel(new BorderLayout());

		cardSalesResult.add(adminSplitPane3);
		cardManageMenu.add(menuSplitPane2);

		cards.add(cardSalesResult, "SALES_RESULT");
		cards.add(cardManageMenu, "MANAGE_MENU");
		
		

		add(cards);
		invokeSplitPane();

		setVisible(true);
		setResizable(false);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void invokeSplitPane() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                adminSplitPane1.setDividerLocation(50 + adminSplitPane1.getInsets().left);
                adminSplitPane2.setDividerLocation(440+adminSplitPane2.getInsets().left);
                adminSplitPane3.setDividerLocation(30+adminSplitPane3.getInsets().bottom);
                adminCenterSplitPane.setDividerLocation(20+adminCenterSplitPane.getInsets().bottom);

                adminSplitPane1.setEnabled(false);
                adminSplitPane1.setDividerSize(1);

                adminSplitPane2.setEnabled(false);
                adminSplitPane2.setDividerSize(1);

                adminSplitPane3.setEnabled(false);
                adminSplitPane3.setDividerSize(1);
                
                adminCenterSplitPane.setEnabled(false);
                adminCenterSplitPane.setDividerSize(1);
            }
        });
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object source = e.getSource();
		System.out.println(source);
		if (source instanceof JButton) {
			String name = ((JButton) e.getSource()).getName();
			switch(name) {
				case "MANAGE_MENU": showManageMenu(); break;
				case "SALES_RESULT": showSalesResult(); break;
				default:
					break;
			}
		}
	}

	private void showManageMenu() {
		System.out.println("메뉴 관리 창으로 넘어갑니다.");
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, "MANAGE_MENU");
	}
	
	private void showSalesResult() {
		System.out.println("매출 결과로 되돌아 갑니다.");
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, "SALES_RESULT");
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}

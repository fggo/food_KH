package foodapp.gui.layout;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import foodapp.dao.UserRepository;
import foodapp.model.vo.Admin;
import foodapp.model.vo.Food;
import foodapp.model.vo.FoodMenu;

public class AdminPageFrame extends JFrame implements MouseListener {
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
	private JToggleButton modifyBtn;
	private JButton deleteBtn;


	private DefaultTableModel tableModel, categoryTableModel;
	
	private JTable menuTable, categoryTable;
	private JScrollPane scrollPane;

	private JSplitPane menuSplitPane1, menuSplitPane2;
	private JPanel topPanel;
	private JPanel bottomPanel;

	private UserRepository userRepo;


	//for admin page
	private JFrame categoryFrame;
	private JRadioButton noodle;
    private JRadioButton soup;
    private JRadioButton rice;
 
    private ButtonGroup buttonGroup;
	private JPanel categoryPanel;
	private JButton okBtn;
	private JSplitPane splitPane;

	private JPanel l1, l2, l3, l4;
	private JPanel r1, r2, r3, r4;

	private JLabel menuCategoryLabel;
	private JLabel menuNoLabel;
	private JLabel menuNameLabel;
	private JLabel menuPriceLabel;


	private JTextField menuNoTxtField;
	private JTextField menuNameTxtField;
	private JTextField menuPriceTxtField;
	
	private DefaultTableModel modelN, modelS, modelR;

	public AdminPageFrame(DefaultTableModel modelN, DefaultTableModel modelS, DefaultTableModel modelR,
			UserRepository userRepo) {
		this.modelN = modelN;
		this.modelS = modelS;
		this.modelR = modelR;

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
			msg += "    " + food + " - - - 총 " + qty + " 개.";
			if(++count < salesResult.size()) msg+="\n";
		}
		salesResultTextArea.setText(msg);
		Font font = new Font("맑은고딕", Font.BOLD, 11);
        salesResultTextArea.setFont(font);
        salesResultTextArea.setForeground(Color.BLUE);
//		scrollTextArea = new JScrollPane(salesResultTextArea,
//				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        topPanel = salesLabelPanel;
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(salesResultTextArea);
		adminCenterSplitPane.setTopComponent(topPanel);
		adminCenterSplitPane.setBottomComponent(bottomPanel);

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
		modifyBtn = new JToggleButton("수 정");
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
		
		initMenuList();

//		String[] colNames = {"카테고리", "메뉴번호", "메뉴이름", "가격"};
//        tableModel = new DefaultTableModel(colNames, 0);
//
//		String[][] menuTableContents = new String[userRepo.getFoodMenu().getFoodMenuList().size()][colNames.length];
//
//		List<Food> foodMenuList = (ArrayList<Food>)userRepo.getFoodMenu().getFoodMenuList();
//
//		Iterator<Food> it = foodMenuList.iterator();
//
//		food = null;
//		count =0;
//		while(it.hasNext()) {
//			food = it.next();
//			
//			String[] row = new String[] { food.getMenuCategory(), 
//						String.valueOf(food.getMenuNo()), 
//						food.getMenuName(), 
//						String.valueOf(food.getMenuPrice()),
//					};
//			menuTableContents[count++] = row;
//		}
//		
//		for(int i =0;  i<menuTableContents.length; i++)
//			tableModel.addRow(menuTableContents[i]);
//
//		menuTable = new JTable(tableModel);

//		tableModel = new DefaultTableModel() {
//			private static final long serialVersionUID = 1L;
//
//			@Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//		};

//		new JTable() {
//	        private static final long serialVersionUID = 1L;
//
//	        @Override
//	        public boolean isCellEditable(int row, int column) {                
//	                return false;               
//	        };
//	    };

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
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

			}
		});
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
	
	private void addMenu() {
		categoryFrame = new JFrame("음식 카테고리 선택");
		categoryFrame.setSize(300, 300);
		categoryFrame.setLocation(this.getX() , this.getY());
		categoryFrame.setLayout(new BorderLayout());

		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(230 + splitPane.getInsets().top);
		splitPane.setDividerSize(1);
		splitPane.setEnabled(false);

		categoryPanel = new JPanel(new GridLayout(4,2));

		l1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		l2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		l3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		l4 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		r1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		r2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		r3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		r4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		menuCategoryLabel = new JLabel("카테고리");
		menuNoLabel = new JLabel("메뉴번호");
		menuNameLabel = new JLabel("메뉴이름");
		menuPriceLabel = new JLabel("음식 가격");

		menuNoTxtField = new JTextField("", 15);
		menuNoTxtField.setEditable(false);
		menuNameTxtField = new JTextField("", 15);
		menuPriceTxtField = new JTextField("", 15);

//		noodle = new JRadioButton("면 요리");
//        soup = new JRadioButton("탕 요리");
//        rice = new JRadioButton("밥 요리");
//        noodle.setSelected(true);
//        buttonGroup = new ButtonGroup();
//        buttonGroup.add(noodle);
//        buttonGroup.add(soup);
//        buttonGroup.add(rice);
//        categoryPanel.add(noodle);
//        categoryPanel.add(soup);
//        categoryPanel.add(rice);

		this.createCategoryTable();
		this.updateNextMenuNo();

		l1.add(menuCategoryLabel); 	r1.add(categoryTable);
		l2.add(menuNoLabel); 		r2.add(menuNoTxtField);
		l3.add(menuNameLabel); 		r3.add(menuNameTxtField);
		l4.add(menuPriceLabel); 	r4.add(menuPriceTxtField);
 
		categoryPanel.add(l1); categoryPanel.add(r1);
		categoryPanel.add(l2); categoryPanel.add(r2);
		categoryPanel.add(l3); categoryPanel.add(r3);
		categoryPanel.add(l4); categoryPanel.add(r4);

		okBtn = new JButton("확인");
		okBtn.setName("ADD_CONFIRM");
		okBtn.addMouseListener(this);
		
		categoryTable.setName("MENU_CATEGORY");
		categoryTable.addMouseListener(this);

		splitPane.setTopComponent(categoryPanel);
		splitPane.setBottomComponent(okBtn);
		
		categoryFrame.add(splitPane);

		categoryFrame.setResizable(false);
		categoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		categoryFrame.setVisible(true); 
	}
	
	private void modifyMenu() {
		if(modifyBtn.isSelected()) {
			addBtn.setEnabled(false);
			deleteBtn.setEnabled(false);

		}
		else {
			FoodMenu menu = (FoodMenu)userRepo.getFoodMenu();

			//dialog
			int result = JOptionPane.showConfirmDialog(null, "수정을 완료 하시겠습니까?", "메뉴 수정 확인",
					JOptionPane.OK_CANCEL_OPTION);

			if(result!= JOptionPane.OK_OPTION) {
				System.out.println("메뉴 수정을 취소합니다.");
				return;
			}
			
			addBtn.setEnabled(true);
			deleteBtn.setEnabled(true);

			int row = menuTable.getSelectedRow();

			String menuCategory = (String)tableModel.getValueAt(row, 0);
			int menuNo = Integer.valueOf((String)tableModel.getValueAt(row, 1));
			String menuName=  (String)tableModel.getValueAt(row, 2);
			int menuPrice = Integer.valueOf((String)tableModel.getValueAt(row, 3));

			Food food = new Food(menuCategory, menuNo, menuName, menuPrice);
		}
	}
	
	private void deleteMenu() {
		//dialog
		int result = JOptionPane.showConfirmDialog(null, "메뉴 삭제를 하시겠습니까?", "메뉴 삭제 확인",
				JOptionPane.OK_CANCEL_OPTION);

		if(result!= JOptionPane.OK_OPTION) {
			System.out.println("메뉴 삭제를 취소합니다.");
			return;
		}
		int row = menuTable.getSelectedRow();
		if(row== -1) {
			JOptionPane.showMessageDialog(null, "테이블 행이 선택되지 않았습니다.", "테이블 행 미선택", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String menuCategory = (String)tableModel.getValueAt(row, 0);
		int menuNo = Integer.valueOf((String)tableModel.getValueAt(row, 1));
		String menuName=  (String)tableModel.getValueAt(row, 2);
		int menuPrice = Integer.valueOf((String)tableModel.getValueAt(row, 3));

		Food food = new Food(menuCategory, menuNo, menuName, menuPrice);
		FoodMenu menu = userRepo.getFoodMenu();
		boolean isRemoved = menu.removeFood(food);

		if (isRemoved) {
			JOptionPane.showMessageDialog(null, "메뉴삭제를 완료 했습니다.", "메뉴 삭제 성공", JOptionPane.WARNING_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(null, "메뉴삭제에 실패했습니다.", "메뉴 삭제 에러", JOptionPane.WARNING_MESSAGE);
		}

		tableModel.removeRow(row);
		tableModel.fireTableDataChanged();

		String[] deleteRow = new String[] { food.getMenuCategory(), 
					String.valueOf(food.getMenuNo()), 
					food.getMenuName(), 
					String.valueOf(food.getMenuPrice()), };
		switch(food.getMenuCategory()) {
			case "NOODLE": removeRow(modelN, deleteRow); modelN.fireTableDataChanged(); break;
			case "SOUP": removeRow(modelS, deleteRow); modelS.fireTableDataChanged(); break;
			case "RICE": removeRow(modelR, deleteRow); modelR.fireTableDataChanged(); break;
		}
	}
	
	private void removeRow(DefaultTableModel model, String[] deleteRow) {
		Food food = null, foodToDelete = null;
		int price = 0;
		for(int i =0; i<model.getRowCount(); i++) {
			price = Integer.valueOf(((String)model.getValueAt(i, 3)).substring(1).replace(",", ""));
			food = new Food((String)model.getValueAt(i, 0), 
					Integer.valueOf((String)model.getValueAt(i, 1)), 
					(String)model.getValueAt(i, 2), 
					price);
			price = Integer.valueOf(deleteRow[3].substring(1).replace(",", ""));
			foodToDelete = new Food(deleteRow[0], 
					Integer.valueOf(deleteRow[1]), 
					deleteRow[2], 
					Integer.valueOf(deleteRow[3]));

			if(food.equals(foodToDelete))
				model.removeRow(i);
		}
	}

	private void initMenuList() {
		String[] colNames = {"카테고리", "메뉴번호", "메뉴이름", "가격"}; 
		tableModel = new DefaultTableModel(colNames, 0);

		String[][] menuTableContents = new String[userRepo.getFoodMenu().getFoodMenuList().size()][colNames.length];

		List<Food> foodMenuList = (ArrayList<Food>)userRepo.getFoodMenu().getFoodMenuList();

		Collections.sort(foodMenuList, (i,j)->{
			return i.getMenuCategory().compareTo(j.getMenuCategory()) == 0 ? 
						i.getMenuNo() - j.getMenuNo(): i.getMenuCategory().compareTo(j.getMenuCategory());
		});

		Iterator<Food> it = foodMenuList.iterator();
		Food food = null;
		int count =0;
		while(it.hasNext()) {
			food = it.next();
			
			String[] row = new String[] { food.getMenuCategory(), 
						String.valueOf(food.getMenuNo()), 
						food.getMenuName(), 
						String.valueOf(food.getMenuPrice()),
					};
			menuTableContents[count++] = row;
		}
		
		for(int i =0;  i<menuTableContents.length; i++)
			tableModel.addRow(menuTableContents[i]);

		menuTable = new JTable(tableModel);
	}

	
	private void createCategoryTable() {
		String[] colNames = {"카테고리"};
		categoryTableModel = new DefaultTableModel(colNames, 0);

		List<Food> foodMenuList = (ArrayList<Food>)userRepo.getFoodMenu().getFoodMenuList();
		Iterator<Food> itr = foodMenuList.iterator();
		Set<String> set = new HashSet<String>();
		Food food = null;
		while(itr.hasNext()) {
			set.add(itr.next().getMenuCategory());
		}
		
		//set : foodMenuCategory
		Iterator<String> itr2 = set.iterator();
		String[][] categoryContents = new String[set.size()][colNames.length];

		String category = "";
		int count =0;
		while(itr2.hasNext()) {
			category = itr2.next();
			
			String[] row = new String[] { category };
			categoryContents[count++] = row;
		}
		
		for(int i =0;  i<categoryContents.length; i++)
			categoryTableModel.addRow(categoryContents[i]);

		categoryTable = new JTable(categoryTableModel);
	}

	private void updateNextMenuNo() {
		int row = categoryTable.getSelectedRow();
		if(row == -1)
			return;
		String menuCategory = ((String)categoryTableModel.getValueAt(row, 0));

		List<Food> foodMenuList = (ArrayList<Food>)userRepo.getFoodMenu().getFoodMenuList();
		Iterator<Food> itr = foodMenuList.iterator();
		Food food = null;

		int max = -1;
		List<Integer> menuNos= new ArrayList<Integer>();
		while(itr.hasNext()) {
			food = itr.next();
			if(food.getMenuCategory().equals(menuCategory)) {
				menuNos.add(food.getMenuNo());
				max = Math.max(food.getMenuNo(), max);
			}
		}

		int newMenuNo = -1;
		for(int i =max; i>0; i--) {
			if(!menuNos.contains(i)) {
				newMenuNo = i;
			}
		}
		if(newMenuNo == -1)
			newMenuNo = max+1;

		this.menuNoTxtField.setText(String.valueOf(newMenuNo));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Object source = e.getSource();
		System.out.println(source);
		if (source instanceof JButton) {
			String name = ((JButton) e.getSource()).getName();
			switch(name) {
				case "MANAGE_MENU": showManageMenu(); break;
				case "SALES_RESULT": showSalesResult(); break;
				case "ADD": addMenu(); break;
				case "ADD_CONFIRM": addConfirm(); break;
				case "DELETE": deleteMenu(); break;
				default:
					break;
			}
		}
		else if(source instanceof JToggleButton) {
			String name = ((JToggleButton) e.getSource()).getName();
			switch(name) {
				case "MODIFY": modifyMenu(); break;
				default:
					break;
			}
		}
		else if(source instanceof JTable) {
			String name = ((JTable)e.getSource()).getName();
			switch(name) {
				case "MENU_CATEGORY": updateNextMenuNo(); break;
			}
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	

	private void addConfirm() {
		int row = categoryTable.getSelectedRow();
		if(row == -1
				|| menuNoTxtField.getText().equals("")
				|| menuNameTxtField.getText().equals("")
				|| menuPriceTxtField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "입력또는 선택하지 않은 데이터가 있습니다.", "데이터 미입력", JOptionPane.WARNING_MESSAGE);
			return;
		}
		try {
		     Integer.parseInt(menuPriceTxtField.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "메뉴 가격은 숫자만 가능합니다.", "가격 정보 에러", JOptionPane.WARNING_MESSAGE);
		}

		String menuCategory = ((String)categoryTableModel.getValueAt(row, 0));
		int menuNo = Integer.valueOf(menuNoTxtField.getText());
		String menuName = menuNameTxtField.getText();
		int menuPrice = Integer.valueOf(menuPriceTxtField.getText());

		Food food = new Food(menuCategory, menuNo, menuName, menuPrice);

		FoodMenu menu = userRepo.getFoodMenu();
		if(menu.contains(food)){
			JOptionPane.showMessageDialog(null, "중복되는 음식메뉴입니다.", "메뉴추가 오류", JOptionPane.WARNING_MESSAGE);
			return;
		}
		else
			menu.addFood(food);

		JOptionPane.showMessageDialog(null, "메뉴추가 완료되었습니다.", "메뉴추가 완료", JOptionPane.WARNING_MESSAGE);
		
		tableModel.addRow(new String[] { food.getMenuCategory(), 
						String.valueOf(food.getMenuNo()), 
						food.getMenuName(), 
						String.valueOf(food.getMenuPrice()),
					});
		tableModel.fireTableDataChanged();


		String[] newRow = new String[] { food.getMenuCategory(), 
					String.valueOf(food.getMenuNo()), 
					food.getMenuName(), 
					food.toCurrency(food.getMenuPrice()) };
		switch(food.getMenuCategory()) {
			case "NOODLE": modelN.addRow(newRow); modelN.fireTableDataChanged(); break;
			case "SOUP": modelS.addRow(newRow); modelS.fireTableDataChanged(); break;
			case "RICE": modelR.addRow(newRow); modelR.fireTableDataChanged(); break;
		}

//		Collections.sort(foodMenuList, (i,j)->{
//			return i.getMenuCategory().compareTo(j.getMenuCategory()) == 0 ? 
//						i.getMenuNo() - j.getMenuNo(): i.getMenuCategory().compareTo(j.getMenuCategory());
//		});

	}
}

package foodapp.gui.event;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import foodapp.dao.UserRepository;
import foodapp.model.vo.Food;

public class FoodCategoryEventHandler extends MouseAdapter {
	private JPanel menuCards;
	JTextField menuCategoryTxt;
	JComboBox menuChoiceComboBox;
	UserRepository userRepo;


	public FoodCategoryEventHandler(JPanel menuCards, JTextField menuCategoryTxt,
			JComboBox menuChoiceComboBox, UserRepository userRepo) {
		super();
		this.menuCards = menuCards;
		this.menuCategoryTxt = menuCategoryTxt;
		this.menuChoiceComboBox = menuChoiceComboBox;
		this.userRepo = userRepo;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() instanceof JButton) {
			String name = ((JButton)e.getSource()).getName();
			switch(name) {
				case "NOODLE": showNoodleMenu(); break;
				case "SOUP": showSoupMenu(); break;
				case "RICE": showRiceMenu(); break;
				default:
					break;
			}
		}
	}

	/* 메뉴 */
	private void showNoodleMenu() {
		System.out.println("면메뉴를 보여줍니다");
		CardLayout cl = (CardLayout)(menuCards.getLayout());
        cl.show(menuCards, "NOODLE");
        this.menuCategoryTxt.setText("면 메뉴");
        getMenuNos(menuChoiceComboBox, "NOODLE");
	}

	private void showSoupMenu() {
		System.out.println("탕 메뉴를 보여줍니다");
		CardLayout cl = (CardLayout)(menuCards.getLayout());
        cl.show(menuCards, "SOUP");
        this.menuCategoryTxt.setText("탕 메뉴");
        getMenuNos(menuChoiceComboBox, "SOUP");
	}

	private void showRiceMenu() {
		System.out.println("밥 메뉴를 보여줍니다.");
		CardLayout cl = (CardLayout)(menuCards.getLayout());
        cl.show(menuCards, "RICE");
        this.menuCategoryTxt.setText("밥 메뉴");
        getMenuNos(menuChoiceComboBox, "RICE");
	}

	private void getMenuNos(JComboBox comboBox, String menuCategory) {
		if(comboBox == null) return;
		List<Food> foodlist = this.userRepo.getFoodMenu().getFoodMenuList();
		Iterator<Food> itr = foodlist.iterator();
		Food food = null;
		int count = comboBox.getItemCount();
		if(comboBox.getItemCount() > 0) {
			comboBox.removeAllItems();
		}
		else {
			while(itr.hasNext()) {
				food = itr.next();
				if(menuCategory.equals(food.getMenuCategory())) {
					comboBox.addItem(food);
				}
			}
		}
	}

}

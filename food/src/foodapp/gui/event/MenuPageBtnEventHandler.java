package foodapp.gui.event;

import static foodapp.dao.Constants.FOOD_MENU_PAGE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;

public class MenuPageBtnEventHandler implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		System.out.println(source);

		if (source instanceof JButton 
				&& ((JButton) source).getName().equals(FOOD_MENU_PAGE)) {
			System.out.println("음식 메뉴 페이지로 이동합니다.");
		}
	}

}

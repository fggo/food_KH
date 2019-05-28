package foodapp.gui.event;

import static foodapp.dao.Constants.FOOD_MENU_PAGE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;

public class MenuItemEventHandler implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		System.out.println(source);

		if (source instanceof JMenuItem) {
			JMenuItem clickedMenuItem = (JMenuItem) source;
			String menuText = clickedMenuItem.getText();
			System.out.println(menuText);
		}
	}

}

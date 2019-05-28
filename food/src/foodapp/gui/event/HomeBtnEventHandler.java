package foodapp.gui.event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeBtnEventHandler extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getSource());
	}

}

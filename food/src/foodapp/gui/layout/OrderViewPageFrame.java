package foodapp.gui.layout;

import static foodapp.dao.Constants.*;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class OrderViewPageFrame extends JPanel implements ActionListener {
	JButton logoBtn;
	CardLayout cl;
	JPanel cards;

	public OrderViewPageFrame(CardLayout cl, JPanel cards) {
		this.logoBtn = new JButton("뒤로가기");
		this.cl = cl;
		this.cards = cards;
		
		logoBtn.addActionListener(this);
		add(logoBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cl.show(cards, INIT_PAGE);
	}

}

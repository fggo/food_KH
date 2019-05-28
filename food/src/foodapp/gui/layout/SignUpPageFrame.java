package foodapp.gui.layout;

import static foodapp.dao.Constants.*;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class SignUpPageFrame extends JPanel implements ActionListener {
	private JButton logoBtn;
	private CardLayout cl;
	private JPanel cards;

	
	
	public SignUpPageFrame(CardLayout cl, JPanel cards) {
		this.cl = cl;
		this.cards = cards;

		initialize();
	}

	private void initialize() {
		this.logoBtn = new JButton("뒤로가기");
		
		logoBtn.addActionListener(this);
		add(logoBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		cl.previous(cards);
		cl.show(cards, INIT_PAGE);
	}

}
package foodapp.gui.layout;

import static foodapp.dao.Constants.INIT_PAGE;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import foodapp.dao.UserRepository;
import foodapp.model.vo.Food;
import foodapp.model.vo.User;

public class OrderViewPageFrame extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton homeBtn;
	private JTextArea orderViewTextArea;

	private CardLayout cl;
	private JPanel cards;
	private JTextField phoneTextField;
	private UserRepository userRepo;


	public OrderViewPageFrame(CardLayout cl, JPanel cards, 
			JTextField phoneTextField, UserRepository userRepo) {
		this.userRepo = userRepo;
		this.cl = cl;
		this.cards = cards;
		this.phoneTextField = phoneTextField;

		initialize();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cl.show(cards, INIT_PAGE);
	}

	private void initialize() {
		setLayout(new BorderLayout());

		homeBtn = new JButton("뒤로가기");
		homeBtn.addActionListener(this);
		add(homeBtn);

		orderViewTextArea = new JTextArea(400, 400);
		add(orderViewTextArea);


		List<User> users = userRepo.getUsers();
		if(users == null)
			return;

		Iterator<User> itr = users.iterator();

		Map<Food, Integer> orderList = new TreeMap<Food, Integer>();

		User user = null;
		while(itr.hasNext()) {
			user = itr.next();
			if(user.getPhone().equals(phoneTextField.getText()))
				orderList = user.getOrderList();
		}


		if (orderList.size() ==0) {
			return;
		}

		String msg = "";
		Food food = null;
		int qty = 0;
		int sum = 0;
		for(Map.Entry<Food, Integer> entry : orderList.entrySet()) {
			food = entry.getKey();
			qty = entry.getValue();
			msg += food + " - - -  " + qty + " 개.\n";
			sum += food.getMenuPrice() * qty;
		}
		msg += " 총 주문 액: " + sum + "원";
		orderViewTextArea.append(msg);
		add(orderViewTextArea);
	}
}

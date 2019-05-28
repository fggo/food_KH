package foodapp.model.vo;

import java.io.Serializable;
import java.util.List;

public class FoodMenu implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<Food> foodMenu;
	
	public FoodMenu() {}
	
	public void addFood() {
	}

	public List<Food> getFoodMenu() { return foodMenu; }
	public void setFoodMenu(List<Food> foodMenu) { this.foodMenu = foodMenu; }
}

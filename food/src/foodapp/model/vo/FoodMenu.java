package foodapp.model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FoodMenu implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private List<Food> foodMenuList;
	
	public FoodMenu() {
		if (foodMenuList == null)
			foodMenuList = new ArrayList<Food>();
	}
	
	public boolean addFood(Food food) {
		return foodMenuList.add(food);
	}

	public boolean removeFood(Food food) {
		return foodMenuList.remove(food);
	}

	public List<Food> getFoodMenuList() { return foodMenuList; }
	public void setFoodMenuList(List<Food> foodMenuList) { this.foodMenuList = foodMenuList; }
}

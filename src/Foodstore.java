import java.util.HashMap;

public class Foodstore {
	
	private HashMap<String, Integer> foodStore;
	
	String[] foodNames;
	
	// put every food into the hash map with 0 amount
	// this prevents exception while calling foodStore.get(foodName)
	Foodstore() {
		foodStore = new HashMap<String, Integer>();
		foodNames = Food.foodNames();
		
		for (int i = 0;i < foodNames.length;i++) {
			foodStore.put(foodNames[i], 0);
		}
	}
	
	public void addFood(String food, Integer number) {
		food = food.toLowerCase();
		if (foodStore.containsKey(food)) {
			foodStore.put(food, (foodStore.get(food) + number));
		} else {
			foodStore.put(food, number);
		}
	}
	
	// return the Food object
	// if the food is not found in the food store, throws an Exception
	// this method throws an Exception in order for the animal to notice and try to take the other food that it eats
	public Food takeFood(String food) throws Exception{
		if (foodStore.containsKey(food)) {
			if (foodStore.get(food) > 0) {
				foodStore.put(food, (foodStore.get(food) - 1));
				Food newFood = getFoodWithName(food);
				if (newFood != null) {
					return newFood;
				}
			} else 
				throw new Exception("not enough food");
		}
		throw new Exception("no such food");
	}
	
	// private method that returns the Food object with the name of food provided
	// if the food can't be found, return null
	private Food getFoodWithName(String nameOfFood) {
		
		for (int i = 0;i < foodNames.length;i++) {
			if (nameOfFood.equals(foodNames[i])) {
				return new Food(i);
			}
		}
		return null;
	}
	
	// take food with large quantity
	// if the amount required is larger than the amount in food store
	// return the amount of the food in the food store
	// this method is only used by zoo keepers to move food from zoo foodstore to the enclosure foodstore
	public Integer takeFood(String food, Integer number) throws Exception {
		if (foodStore.containsKey(food)) {
			Integer amount = foodStore.get(food);
			if (amount >= number) {
				foodStore.put(food, (foodStore.get(food) - number));
				return number;
			} else if (amount > 0) {
				foodStore.put(food, 0);
				return amount;
			} else 
				throw new Exception("not enough food");
		} else 
			throw new Exception("no such food");
	}
	
	// this method returns the name of the food with the lowest amount
	public String getFoodWithLowestAmount() {
		String foodName = null;
		Integer min = 10000;
		for (String food: foodStore.keySet()) {
			Integer amount = foodStore.get(food);
            if (amount < min) {
            	min = amount;
            	foodName = food;
            }
		} 
		return foodName;
	}
	
	// returns a duplicate of the food store's hashmap but without food that has 0 amount
	public HashMap<String, Integer> getFoodstoreMap() {
		HashMap<String, Integer> foodMap = new HashMap<String, Integer>();
		
		for (String food : foodStore.keySet()) {
			Integer amount = foodStore.get(food);
			if (amount > 0) {
				foodMap.put(food, amount);
			}
		}
		
		return foodMap;
	}
	
	public void printMonthlyReport() {
		for (String food: foodStore.keySet()) {
            Integer amount = foodStore.get(food);  
            System.out.println(food + ": " + amount + " left");  
		} 
	}
}

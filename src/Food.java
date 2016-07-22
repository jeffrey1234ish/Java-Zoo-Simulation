public class Food {
	
	String name;
	Integer health;
	Integer waste;
	
	private static int foodModel[][] = 
		{{1, 4}, {3, 4}, {2, 3}, {0, 1}, {3, 2}, {1, 3}};
	private static String foodNames[] = 
		{"hay", "steak", "fruit", "celery", "fish", "icecream"};
	
	public Food(int type) {
		for (int i = 0;i < 6;i++) {
			health = foodModel[i][0];
			waste = foodModel[i][1];
			name = foodNames[i];
		}
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getHealth() {
		return health;
	}
	
	public Integer getWaste() {
		return waste;
	}
	
	// static method that returns the names of the food
	public static String[] foodNames() {
		return foodNames;
	}
	
}
	

public abstract class Animal implements Comparable<Animal>{
	
	protected Integer age;
	protected char gender;
	protected String[] eats;
	protected Integer health;
	protected Integer lifeExpectancy;
	protected Enclosure enclosure;
	
	// boolean variables used to identifu type of zoo keeper needed for the animal
	protected boolean needsPlayZookeeper;
	protected boolean needsPhysioZookeeper;
	
	public Animal(Integer age, char gender, Integer health) {
		this.age = age;
		this.gender = gender;
		this.health = health;
		
		this.needsPlayZookeeper = false;
		this.needsPhysioZookeeper = false;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public Integer getHealth() {
		return health;
	}
	
	public char getGender() {
		return gender;
	}
	
	public Integer getLifeExpectancy() {
		return lifeExpectancy;
	}
	
	public void setEnclosure(Enclosure newEnclosure) {
		enclosure = newEnclosure;
	}
	
	public boolean canEat(String food) {
		for (String each : eats) {
			if (each.equals(food)) {
				return true; 
			}
		}
		return false;
	}
	
	// try to take food that the animal likes from the enclosure's food store
	// if the food is not found, try and take the second type of food that the animal likes
	// if the second food is not found either, then throw an Exception 
	public void eat() {
		Foodstore foodStore = enclosure.getFoodstore();
		Food foodToke;
		
		try {
			foodToke = foodStore.takeFood(eats[0]);
			enclosure.addWaste(foodToke.getWaste());
			this.increaseHealth(foodToke.getHealth());
		} catch (Exception e) {
			try {
				foodToke = foodStore.takeFood(eats[1]);
				enclosure.addWaste(foodToke.getWaste());
				this.increaseHealth(foodToke.getHealth());
			} catch (Exception e1) {
				System.out.println("An animal couldn't find food to eat");
			}
		}
		
	}
	
	// increase the health of animal by the value passed in the parameter but maximum 10
	public void increaseHealth(Integer health) {
		this.health += health;
		if (this.health > 10) {
			this.health = 10;
		}
	}
	
	// decrease the health of animal by 2 but minimum 0
	// This method is used only in the Animals aMonthPasses method
	// The amount of health decrease should depends on the age of the animal and the life expectancy of the animal
	public void decreaseHealth() {
		Integer healthFactor = this.age/10;
		this.health -= (2 + healthFactor);
		if (this.health < 0) {
			this.health = 0;
		}
	}
	
	// abstract method that allows each type of animal to be treated differently
	public abstract void treat();
	
	public abstract boolean aMonthPasses();
	
	public int compareTo(Animal o) {
		return Integer.compare(this.health, o.health);
	}
	
}

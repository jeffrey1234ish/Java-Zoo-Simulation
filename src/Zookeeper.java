import java.util.Collections;
import java.util.Iterator;

public class Zookeeper {
	protected Enclosure enclosure;
	
	protected String id;
	
	// private variables that stores values for the monthly report
	private String foodToke;
	private Integer amountAdded, wasteRemoved;
	
	Zookeeper(Enclosure enclosure) {
		this.enclosure = enclosure;
	}
	
	public void aMonthPassess(Zoo zoo) {
		cleanUpWaste();
		treatAnimals();
		moveFood(zoo);
	}
	
	private void cleanUpWaste() {
		wasteRemoved = enclosure.removeWaste(20);
	}
	
	// Zoo keepers will treat animals by first sorting them by their health value
	// the two animals that have the lowest health value will be treated
	// this will keep the health of the animals distributed normally
	private void treatAnimals()  {
		enclosure.sortAnimals();
		
		int treatedAnimalCount = 0;
		Iterator<Animal> itr = enclosure.getAnimalIterator();
		while (itr.hasNext() && treatedAnimalCount < 2) {
			Animal animal = itr.next();
			if  (!(animal.needsPhysioZookeeper || animal.needsPlayZookeeper) || 
					(this instanceof PlayZookeeper && animal.needsPlayZookeeper) || 
					(this instanceof PhysioZookeeper && animal.needsPhysioZookeeper) ) 
			{
				animal.treat();
				System.out.println("Zookeeper: " + animal.getClass().getTypeName() + " treated");
				treatedAnimalCount += 1;
			} 
		}
	}
	
	// Get the food with the lowest amount in the food store of the enclosure
	// take 20 of the food from the zoo's food store and add them into the enclosure's food store
	private void moveFood(Zoo zoo) {
		Foodstore enclosureFoodstore = enclosure.getFoodstore();
		Foodstore zooFoodstore = zoo.getFoodStore();
		
		String needFood = enclosureFoodstore.getFoodWithLowestAmount();
		try {
			Integer amount = zooFoodstore.takeFood(needFood, 20);
			foodToke = needFood;
			enclosureFoodstore.addFood(needFood, amount);
			amountAdded = amount;
		} catch (Exception e) {
			System.out.println("A " + this.getClass().getTypeName() + " failed when trying to move food from zoo to enclosure.");
		}
	}
	
	public void printMonthlyReport() {
		System.out.println("[" + this.getClass().getTypeName() + ": Added " + amountAdded + " " + foodToke + " to enclosure. Cleaned " + wasteRemoved + " waste ]");
	}
}

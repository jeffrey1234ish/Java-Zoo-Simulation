import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Enclosure {
	private ArrayList<Animal> animals;
	private ArrayList<Zookeeper> zookeepers;
	private Foodstore foodStore;
	private int animalWaste;
	
	public Enclosure() {
		animals = new ArrayList<Animal>(20);
		zookeepers = new ArrayList<Zookeeper>();
		
		foodStore = new Foodstore();
		animalWaste = 0;
	}
	
	// add new animal as long as the amount is less than 20
	public boolean addAnimal(Animal animal) {
		if (animals.size() < 20) {
			animal.setEnclosure(this);
			animals.add(animal);
			
			return true;
		}
		return false;
	}
	
	public void addZookeeper(Zookeeper zookeeper) {
		zookeepers.add(zookeeper);
	}
	
	// remove a specific animal
	public void removeAnimal(Animal animal) {
		animals.remove(animal);
	}
	
	// return the animal at index
	public Animal getAnimalAtIndex(int index) {
		return animals.get(index);
	}
	/*
	public Animal[] getFirstTwoAnimals() throws Exception {
		if (animals.size() >= 2) {
			Animal[] newAnimals = new Animal[2];
			newAnimals[0] = this.getAnimalAtIndex(0);
			newAnimals[1] = this.getAnimalAtIndex(1);
			return newAnimals;
		} else
			throw new Exception();
		
	}*/
	
	public int getWaste() {
		return animalWaste;
	}
	
	// remove waste but don't let waste to be less than 0
	// return the total waste removed
	public Integer removeWaste(int waste) {
		Integer wasteRemoved = animalWaste;
		animalWaste -= waste;
		if (animalWaste < 0)
			animalWaste = 0;
		else 
			wasteRemoved = waste;
		
		return wasteRemoved;
	}
	
	public void addWaste(int waste) {
		animalWaste += waste;
	}
	
	public Foodstore getFoodstore() {
		return foodStore;
	}

	public Iterator<Animal> getAnimalIterator() {
		return animals.iterator();
	}
	
	public Iterator<Zookeeper> getZookeeperIterator() {
		return zookeepers.iterator();
	}
	
	// sort animals by health in ascending order
	public void sortAnimals() {
		Collections.sort(animals);
	}
	
	// call the animals aMonthPasses method
	// remove the animal if it's health is less than or equal to 0
	public void aMonthPasses() {
		Iterator<Animal> itr = animals.iterator();
		while (itr.hasNext()) {
			Animal animal = itr.next();
			if (animal.getHealth() <= 0) {
				itr.remove();
			}
			animal.aMonthPasses();
		}
	}

	public void printMonthlyReport() {
		for (Animal animal : animals) {
			System.out.println(animal.getClass().getTypeName() + " \'" + animal.getGender() + "\' : " + animal.getAge() + " months old, " + animal.getHealth() + " health");
		}
		System.out.println("Waste: " + animalWaste);
		foodStore.printMonthlyReport();
	}
}

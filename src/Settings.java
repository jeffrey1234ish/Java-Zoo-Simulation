import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

public class Settings {

	private String filePath;
	private BufferedReader bufferedReader;
	
	private String[] animalNames = {"lion", "tiger", "penguin", "giraffe", "elephant", "bear", "gorilla", "chimpanzee"};
	private String[] foodNames;
	
	private Settings(String fP) {
		filePath = fP;
		
		try {
			bufferedReader = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		foodNames = Food.foodNames();
	}
	
	// A static method that calls the newZoo method with "context.txt" if it exists and returns the Zoo object
	public static Zoo newZoo() throws Exception {
		File f = new File("context.txt");
		if (f.exists())
			return Settings.newZoo("context.txt");
		else 
			throw new Exception("No save file found");
	}
	
	// A static method that parse through the configuration file.
	// It creates a zoo by parsing a configuration file with the file path provided and returns it.
	// The method looks for "zoo" first. If "save" or "time" is found during the process, it stores their value to newZoo object,
	// otherwise everything found else will be ignored.
	// After "zoo" is found, it looks for "enclosure". Food found on the way will be added to the zoo's food store.
	// otherwise everything found else will be ignored.
	// When the method finds "enclosure", it looks for Food, Animal and Zookeeper.
	// They will be addded to the enclosure's food store, animals and zookeepers accordingly. Everything found else will be ignored.
	// This method only parses and creates one zoo according to the specs
	public static Zoo newZoo(String fP) throws Exception{
		Settings set = new Settings(fP);
		Zoo newZoo = new Zoo();
		Enclosure lastEnclosure = null;
		
		try {
			String line;
			String[] key;
			do {
				line = set.getLine();
				key = line.split(":");
				String keyLowerCase = key[0].toLowerCase();
				if (keyLowerCase.equals("save")) {
					newZoo.setSaveEnabled(Boolean.valueOf(key[1]));
				} else if (keyLowerCase.equals("time")) {
					Integer time = Integer.valueOf(key[1]);
					if (time >= 500) {
						newZoo.setTimeBetweenMonths(time);
					} else 
						throw new Exception("Erroneous value: " + "\"" + key[1] + "\" in time");
				}
			} while(!(key[0].equals("zoo")));
			
			line = set.getLine();
			while (line != null) {
				key = line.split(":");
				if (lastEnclosure == null && !(key[0].toLowerCase().equals("enclosure"))) {
					if (set.parseFood(key)) {
						String foodName = key[0].toLowerCase();
						Integer amount = Integer.valueOf(key[1]);
						newZoo.getFoodStore().addFood(foodName, amount);
					} else 
						throw new Exception("Syntax error: unknown key \""+ key[0] + "\" in zoo ");
				} else if (key[0].toLowerCase().equals("enclosure")) {
					Integer waste = Integer.valueOf(key[1]);
					if (waste >= 0) {
						if (lastEnclosure != null)
							newZoo.addEnclosure(lastEnclosure);
						lastEnclosure = new Enclosure();
						lastEnclosure.addWaste(waste);
					} else 
						throw new Exception("Erroneous value: " + "\"" + key[1] + "\" in enclosure");
				} else {
					if (set.parseFood(key)) {
						String foodName = key[0].toLowerCase();
						Integer amount = Integer.valueOf(key[1]);
						lastEnclosure.getFoodstore().addFood(foodName, amount);
					} else if (set.parseAnimal(key)) {
						lastEnclosure.addAnimal(set.animalWithKey(key));
					} else if (set.parseZookeeper(key)) {
						String name = key[0].toLowerCase();
						if (name.equals("zookeeper")) {
							Zookeeper zookeeper = new Zookeeper(lastEnclosure);
							 lastEnclosure.addZookeeper(zookeeper);
							newZoo.addZookeeper(zookeeper);
						} else if (name.equals("playzookeeper")) {
							PlayZookeeper zookeeper = new PlayZookeeper(lastEnclosure);
							lastEnclosure.addZookeeper(zookeeper);
							newZoo.addZookeeper(zookeeper);
						} else if (name.equals("physiozookeeper")) {
							PhysioZookeeper zookeeper = new PhysioZookeeper(lastEnclosure);
							lastEnclosure.addZookeeper(zookeeper);
							newZoo.addZookeeper(zookeeper);
						} 
					} else 
						throw new Exception("Syntax error: unknown key \""+ key[0] + "\" in zoo ");
				}
				line = set.getLine();
			}
			newZoo.addEnclosure(lastEnclosure);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return newZoo;
	}
	
	// This method is used to save the courrent state of the zoo to "context.txt"
	// The method loops through all enclosures, food store, zookeepers and animals in print them in "context.txt"
	// The format of the save file is the same as the configuration file
	public static void saveState(Zoo zoo) throws FileNotFoundException {
		File f = new File("context.txt");
		if (f.exists()) {
			f.delete();
		}
		PrintStream printStream = new PrintStream("context.txt");
		
		printStream.println("zoo:");
		
		Foodstore zooFoodstore = zoo.getFoodStore();
		HashMap<String, Integer> foodMap = zooFoodstore.getFoodstoreMap();
		for (String food : foodMap.keySet()) {
			Integer amount = foodMap.get(food);
			printStream.println(food + ":" + amount);
		} 
		
		Enclosure[] enclosures = zoo.getEnclosures();
		Integer numberOfEnclosures = zoo.getNumberOfEnclosures();
		Foodstore enclosureFoodstore;
		for (int i = 0;i < numberOfEnclosures; i++) {
			printStream.println("enclosure:" + enclosures[i].getWaste());
			enclosureFoodstore = enclosures[i].getFoodstore();
			foodMap = enclosureFoodstore.getFoodstoreMap();
			for (String food : foodMap.keySet()) {
				Integer amount = foodMap.get(food);
				printStream.println(food + ":" + amount);
			} 
			Iterator<Animal> itr = enclosures[i].getAnimalIterator();
			while (itr.hasNext()) {
				Animal animal = itr.next();
				printStream.println(animal.getClass().getTypeName().toLowerCase() + ":" + animal.gender + "," + animal.age + "," + animal.health);
			}
			Iterator<Zookeeper> itr2 = enclosures[i].getZookeeperIterator();
			while (itr2.hasNext()) {
				Zookeeper zookeeper = itr2.next();
				printStream.println(zookeeper.getClass().getTypeName().toLowerCase() + ":");
			}
		 }
		
		printStream.close();
	}
	
	// This method is used to validate the syntax for food
	// Returns true if the key is food and if the amount is more than 0
	// Throws exception if the amount of the food is less than of equal to 0
	private boolean parseFood(String[] key) throws Exception{
		String foodNameLowerCase = key[0].toLowerCase();
		for (int i = 0;i < foodNames.length; i++) {
			if (foodNameLowerCase.equals(foodNames[i])) {
				Integer amount = Integer.valueOf(key[1]);
				if (amount > 0) {
					return true;
				} else
					throw new Exception("Erroneous value: " + "\"" + key[1] + "\" in " + key[0]);
			} 
		}
		return false;
	}
	
	// check if the animal name and the attributes are valid or not
	// Returns true if the key is animal and if the format is correct
	// Throws exception if more than 3 values are entered
	// For example: "lion:f,2,4,5" will result in an Exception being thrown
	private boolean parseAnimal(String[] key) throws Exception{
		String animalNameLowerCase = key[0].toLowerCase();
		for (int i = 0; i < animalNames.length; i++) {
			if (animalNameLowerCase.equals(animalNames[i])) {
				String[] animalValues = key[1].split(",");
				if (animalValues.length <= 3) 
					return true;
				else 
					throw new Exception("Erroneous value: " + "\"" + key[1] + "\" in " + key[0]);
			}
		}
		return false;
	}
	
	// check if the key is zookeeper or not
	// return true if the key is zookeeper
	// return false otherwise
	private boolean parseZookeeper(String[] key) throws Exception {
		String zookeeper = key[0].toLowerCase();
		if (zookeeper.equals("zookeeper") || zookeeper.equals("playzookeeper") || zookeeper.equals("physiozookeeper")) {
			return true;
		}
		return false;
	}
	
	// create and return an Animal with the attributes
	// In the configuration file, an animal can be created with no attributes
	// for example: "lion:" will create a male lion that's 0 month old and has 5 health
	private Animal animalWithKey(String[] key) {
		String animalName = key[0].toLowerCase();
		String[] animalValues = key[1].split(",");
		char gender = 'm';
		Integer age = 0, health = 5;
		
		for (int i = 0;i < animalValues.length; i++) {
			switch (i) {
				case 0: {
					String genderLower = animalValues[0].toLowerCase();
					if (checkGender(genderLower)) {
						if (genderLower.length() == 1)
							gender = genderLower.charAt(0);
					} else
						gender = 'm';
					break;
				}
				case 1: {
					age = Integer.valueOf(animalValues[1]);
					if (age > 36 || age < 0) {
						age = 0;
					}
					break;
				}
				case 2: {
					health = Integer.valueOf(animalValues[2]);
					if (health > 10 || health <= 0) {
						health = 5;
					}
					break;
				}
				default: 
					break;
			}
		}
		
		if (animalName.equals("lion")) {
			Lion lion = new Lion(age, gender, health);
			return lion;
		} else if (animalName.equals("tiger")) {
			Tiger tiger = new Tiger(age, gender, health);
			return tiger;
		} else if (animalName.equals("penguin")) {
			Penguin penguin = new Penguin(age, gender, health);
			return penguin;
		} else if (animalName.equals("giraffe")) {
			Giraffe giraffe = new Giraffe(age, gender, health);
			return giraffe;
		} else if (animalName.equals("gorilla")) {
			Gorilla gorilla = new Gorilla(age, gender, health);
			return gorilla;
		} else if (animalName.equals("chimpanzee")) {
			Chimpanzee chimpanzee = new Chimpanzee(age, gender, health);
			return chimpanzee;
		} else if (animalName.equals("elephant")) {
			Elephant elephant = new Elephant(age, gender, health);
			return elephant;
		} else if (animalName.equals("bear")) {
			Bear bear = new Bear(age, gender, health);
			return bear;
		}

		return null; 
	}

	private boolean checkGender(String gender) {
		return gender.equals("m") || gender.equals("f");
	}
	
	// try to call bufferedReader.readLine() and return the return value of it
	public String getLine() throws IOException {
		return bufferedReader.readLine();
	}
	// try to call bufferedReader.ready() and return the return value of it
	public boolean ready() throws IOException {
		return bufferedReader.ready();
	}
	
	
}

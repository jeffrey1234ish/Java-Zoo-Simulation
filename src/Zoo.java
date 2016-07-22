import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

public class Zoo {
	private Enclosure[] enclosures;
	private ArrayList<Zookeeper> zookeepers;
	private Foodstore zooFoodStore;
	private Integer enclosureCount = 0;
	private Integer month = 0;
	
	// options for simulation
	private Boolean saveEnabled = true;
	private Integer timeBetweenMonths = 2000;
	
	Zoo() {
		enclosures = new Enclosure[10];
		zooFoodStore = new Foodstore();
		zookeepers = new ArrayList<Zookeeper>();
	}
	
	// print out monthly report
	public void printZooMonthlyReport() {
		System.out.println();
		System.out.println("                   " + month + " month              ");
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * ");
		for (int i = 0;i < enclosureCount;i++) {
			System.out.println("==========----- Enclosure " + (i+1) + " -----==========");
			enclosures[i].printMonthlyReport();
			Iterator<Zookeeper> itr = enclosures[i].getZookeeperIterator();
			while (itr.hasNext()) {
				Zookeeper zookeeper = itr.next();
				zookeeper.printMonthlyReport();
			}
			System.out.println("===========================================");
		}
	}
	
	public void printZooLogo() {
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
		System.out.println("*         _       __  __                _       ______             *");
		System.out.println("*        | |     / _|/ _|              ( )     |___  /             *");
		System.out.println("*        | | ___| |_| |_ _ __ ___ _   _|/ ___     / / ___   ___    *");
		System.out.println("*    _   | |/ _ \\  _|  _| \'__/ _ \\ | | | / __|   / / / _ \\ / _ \\   *");
		System.out.println("*   | |__| |  __/ | | | | | |  __/ |_| | \\__ \\  / /_| (_) | (_) |  *");
		System.out.println("*    \\____/ \\___|_| |_| |_|  \\___|\\__, | |___/ /_____\\___/ \\___/   *");
		System.out.println("*                                  __/ |                           *");
		System.out.println("*                                 |___/                            *");
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
	}
	
	public void printOptions() {
		System.out.println("Options:");
		System.out.println("Save enabled: " + saveEnabled);
		System.out.println("Time between months: " + timeBetweenMonths);
		System.out.println();
	}
	
	public Enclosure[] getEnclosures() {
		return enclosures;
	}
	
	public Integer getNumberOfEnclosures() {
		return enclosureCount;
	}
	
	public Boolean getSaveEnabled() {
		return saveEnabled;
	}
	
	public void setSaveEnabled(Boolean bool) {
		saveEnabled = bool;
	}
	
	public void setTimeBetweenMonths(Integer time) {
		timeBetweenMonths = time;
	} 
	
	// call aMonthPasses for all enclosures and zookeepers in the zoo
	// print monthly report and restock the zoo food store
	public void aMonthPasses() {
		month++;
		for (int i = 0;i < enclosureCount;i++) {
			enclosures[i].aMonthPasses();
		}
		for (Zookeeper zookeeper : zookeepers) {
			zookeeper.aMonthPassess(this);
		}
		printZooMonthlyReport();
		restockFoodStore();
	}
	
	public Foodstore getFoodStore() {
		return zooFoodStore;
	} 
	
	// add 50 of each food to the zoo food store;
	private void restockFoodStore() {
		String[] foodNames = Food.foodNames();
		for (int i = 0;i < foodNames.length;i++) {
			zooFoodStore.addFood(foodNames[i], 50);
		}
	}
	
	public void addEnclosure(Enclosure enclosure) {
		if (enclosureCount < 10) {
			enclosures[enclosureCount] = enclosure;
			enclosureCount++;
		}
	}
	
	public void addZookeeper(Zookeeper zookeeper) {
		zookeepers.add(zookeeper);
	}
	
	// ***** The start button for the simulation ******
	// When this method is called, the simulation starts
	// Save state after each month automatically if save feature is enabled
	public void go() {
		printZooLogo(); 
		while (true) {
			System.out.println("Live status:");
			aMonthPasses();
			if (saveEnabled) {
				save();
			}
			try {
				Thread.sleep(timeBetweenMonths);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// try and save the current state of the zoo
	private void save() {
		try {
			System.out.println("Saving context...");
			Settings.saveState(this);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		try {
			Zoo zoo;
			if (args.length > 0) {
				zoo = Settings.newZoo(args[0]);
			}else {
				zoo = Settings.newZoo();
			}
			zoo.printOptions();
			zoo.go();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

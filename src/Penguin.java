
public class Penguin extends Animal {
	
	public Penguin(Integer age, char gender, Integer health) {
		super(age, gender, health);
		
		this.eats = new String[2];
		this.eats[0] = "fish";
		this.eats[1] = "icecream";
		
		this.lifeExpectancy = 15;
		
		this.needsPlayZookeeper = true;
	}
	
	public Penguin(char gender, Integer health) {
		this(0, gender, health);
	}
	
	public Penguin(char gender) {
		this(gender, 10);
	}

	private void watchAFilm() {
		this.increaseHealth(2);
	}
	
	public void treat() {
		watchAFilm();
	}
	
	@Override
	public boolean aMonthPasses() {
		this.decreaseHealth();
		this.eat();
		this.age += 1;
		
		return false;
	}

}

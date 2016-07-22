public class Elephant extends Animal {

	public Elephant(Integer age, char gender, Integer health) {
		super(age, gender, health);
		
		this.eats = new String[2];
		this.eats[0] = "hay";
		this.eats[1] = "fruit";
		
		this.lifeExpectancy = 36;
		
		this.needsPhysioZookeeper = true;
	}
	
	public Elephant(char gender, Integer health) {
		this(0, gender, health);
	}
	
	public Elephant(char gender) {
		this(gender, 10);
	}
	
	private void bath() {
		this.increaseHealth(5);
	}
	
	public void treat() {
		bath();
	}
	
	@Override
	public boolean aMonthPasses() {
		this.decreaseHealth();
		this.eat();
		this.age += 1;
		
		return false;
	}

}


public class Bear extends Animal {

	public Bear(Integer age, char gender, Integer health) {
		super(age, gender, health);
		
		this.eats = new String[2];
		this.eats[0] = "fish";
		this.eats[1] = "steak";
		
		this.lifeExpectancy = 18;
	}
	
	public Bear(char gender, Integer health) {
		this(0, gender, health);
	}
	
	public Bear(char gender) {
		this(gender, 10);
	}

	private void hug() {
		this.increaseHealth(3);
	}
	
	public void treat() {
		hug();
	}
	
	@Override
	public boolean aMonthPasses() {
		this.decreaseHealth();
		this.eat();
		this.age += 1;
		
		return false;
	}

}

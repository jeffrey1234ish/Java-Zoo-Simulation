
public class Giraffe extends Animal {

	public Giraffe(Integer age, char gender, Integer health) {
		super(age, gender, health);
		
		this.eats = new String[2];
		this.eats[0] = "hay";
		this.eats[1] = "fruit";
		
		this.lifeExpectancy = 28;
		
		this.needsPhysioZookeeper = true;
	}
	
	// new born baby Giraffe
	public Giraffe(char gender, Integer health) {
		this(0, gender, health);
	}
	
	// new born baby Giraffe with max health
	public Giraffe(char gender) {
		this(gender, 10);
	}
	
	private void neckMassage() {
		this.increaseHealth(4);
	}
	
	public void treat() {
		neckMassage();
	}

	@Override
	public boolean aMonthPasses() {
		this.decreaseHealth();
		this.eat();
		this.age += 1;
		
		return false;
	}

}

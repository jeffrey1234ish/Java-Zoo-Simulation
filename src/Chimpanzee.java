
public class Chimpanzee extends Ape {

	public Chimpanzee(Integer age, char gender, Integer health) {
		super(age, gender, health);

		this.lifeExpectancy = 24;
		
		this.needsPlayZookeeper = true;
	}
	
	public Chimpanzee(char gender, Integer health) {
		this(0, gender, health);
	}
	
	public Chimpanzee(char gender) {
		this(0, gender, 10);
	}
	
	private void playChase() {
		this.increaseHealth(4);
	}
	
	public void treat() {
		playChase();
	}
	
	@Override
	public boolean aMonthPasses() {
		this.decreaseHealth();
		this.eat();
		this.age += 1;
		
		return false;
	}

}

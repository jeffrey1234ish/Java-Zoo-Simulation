public class Gorilla extends Ape {

	public Gorilla(Integer age, char gender, Integer health) {
		super(age, gender, health);
		
		this.lifeExpectancy = 32;
		this.needsPlayZookeeper = true;
	}
	
	public Gorilla(char gender, Integer health) {
		this(0, gender, health);
	}
	
	public Gorilla(char gender) {
		this(0,  gender, 10);
	}
	
	private void painting() {
		this.increaseHealth(4);
	}
	
	public void treat(){
		painting();
	}
	
	@Override
	public boolean aMonthPasses() {
		this.decreaseHealth();
		this.eat();
		this.age += 1;
		
		return false;
	}

}

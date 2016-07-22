
public abstract class Ape extends Animal {

	public Ape(Integer age, char gender, Integer health) {
		super(age, gender, health);
		
		this.eats = new String[2];
		this.eats[0] = "fruit";
		this.eats[1] = "icecream";
	}
	
	@Override
	public boolean aMonthPasses() {
		this.decreaseHealth();
		this.eat();
		return true;
	}
}

public class Lion extends BigCat {

	public Lion(Integer age, char gender, Integer health) {
		super(age, gender, health);
	}
	
	public Lion(char gender, Integer health) {
		this(0, gender, health);
	}
	
	public Lion(char gender) {
		this(gender, 10);
	}
	
	@Override
	protected void stroke() {
		this.increaseHealth(2);
	}
	
	public void treat() {
		stroke();
	}
	
	@Override
	public boolean aMonthPasses() {
		this.decreaseHealth();
		this.eat();
		this.age += 1;
		
		return true;
	}
}

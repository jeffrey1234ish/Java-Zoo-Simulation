public class Tiger extends BigCat {

	public Tiger(Integer age, char gender, Integer health) {
		super(age, gender, health);
	}
	
	public Tiger(char gender, Integer health) {
		this(0, gender, health);
	}
	
	public Tiger(char gender) {
		this(0, gender, 10);
	}
	
	@Override
	protected void stroke() {
		this.increaseHealth(3);
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

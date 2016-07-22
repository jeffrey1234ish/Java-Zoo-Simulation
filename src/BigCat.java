public abstract class BigCat extends Animal {

	public BigCat(Integer age, char gender, Integer health) {
		super(age, gender, health);
		
		this.eats = new String[2];
		this.eats[0] = "steak";
		this.eats[1] = "celery";
		
		this.lifeExpectancy = 24;
	}
	
	public BigCat(char gender, Integer health) {
		this(0, gender, health);
	}

	protected abstract void stroke();
	
	public void treat() {
		this.stroke();
	}
}

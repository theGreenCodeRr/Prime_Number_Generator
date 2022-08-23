package main;

public class Number {

	private Integer value;
	private boolean isPrime = false;

	//getters & setters
	protected Number(Integer value) {this.value = value;}
	protected Integer getValue() {return value;}
	protected void setValue(Integer value) {this.value = value;}
	protected boolean getPrime() {return isPrime;}
	protected void setPrime(boolean isPrime) {this.isPrime = isPrime;}
	
	//checking prime number
	protected boolean isPrime() {
		int i;
		int number = this.getValue();

		if (number <= 1)
			return false;

		for (i = 2; i * i <= number; i++) {
			if (number % i == 0)
				return false;
		}
		return true;
	}

}

# b07lab1
b07lab1 9.16
package hi;

public class Polynomial {
	public double[] coefficients;
	
	public Polynomial() {
		coefficients = new double[1];
		coefficients[0] = 0;
	}
	
	public Polynomial(double[] co) {
		coefficients = new double[co.length];
		coefficients = co.clone();
	}
	
	public Polynomial Add(Polynomial Po) {
		int max_length = Math.max(this.coefficients.length, Po.coefficients.length);
		double[] result_co = new double[max_length];
		
		for (int i = 0; i < max_length; i++) {
			result_co[i] = 0;
			if (i < this.coefficients.length){
				result_co[i] += this.coefficients[i];
			}
			if (i < Po.coefficients.length) {
				result_co[i] += Po.coefficients[i];
			}
		}
		return new Polynomial(result_co);
	}
	
	public double evaluate(double arg) {
		double result = 0;
		for (int i = 0; i < this.coefficients.length; i++) {
			result += this.coefficients[i] * Math.pow(arg, i);
		}
		return result;
	}
	
	public boolean hasRoot(double arg) {
		return evaluate(arg) == 0;
	}
}

package Driver;

/*public class Driver {
	public static void main(String [] args) {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1 = {6,0,0,5};
		Polynomial p1 = new Polynomial(c1);
		double [] c2 = {0,-2,0,0,-9};
		Polynomial p2 = new Polynomial(c2); 
		Polynomial s = p1.Add(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1)); 
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");
	}
}*/

public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial(); // 默认的多项式
        System.out.println(p.evaluate(3));
        
        // p1: 6x^3 + 0x^2 + 0x^1 + 5
        double[] c1 = {6, 0, 0, 5};
        int[] e1 = {3, 2, 1, 0}; // 对应的指数
        Polynomial p1 = new Polynomial(c1, e1); // 传入系数和指数

        // p2: -2x^4 - 9
        double[] c2 = {0, -2, 0, 0, -9};
        int[] e2 = {4, 3, 2, 1, 0}; // 对应的指数
        Polynomial p2 = new Polynomial(c2, e2);

        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        
        if (s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
    }
}
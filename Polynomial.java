package lab2;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;
import java.io.IOException;

public class Polynomial {
    public double[] coefficients;
    public int[] exponents;

    public Polynomial() {
        coefficients = new double[1];
        coefficients[0] = 0;
        exponents = new int[1];
        exponents[0] = 0;
    }

    public Polynomial(double[] co, int[] expo) {
        this.coefficients = co.clone();
        this.exponents = expo.clone();
    }
    
    public Polynomial(File file) throws IOException{
    	Scanner scan = new Scanner(file);
    	String po = scan.nextLine();
    	scan.close();
    	
    	String[] terms = po.split("(?=[+-])");
    	
    	int[] expo = new int[terms.length];
    	double[] co = new double[terms.length];
    	
    	for (int i = 0; i < terms.length; i++) {
    		if (terms[i].contains("x")) {
    			expo[i] = 0;
    			co[i] = Integer.parseInt(terms[i]);
    		}
    		else {
    			String[] co_expo = terms[i].split("x");

    			if (co_expo[0].isEmpty()) {
    			    co[i] = 1.0;
    			} else if (co_expo[0].equals("-")) {
    			    co[i] = -1.0;
    			} else {
    			    co[i] = Double.parseDouble(co_expo[0]);
    			}

    			if (co_expo.length > 1 && !co_expo[1].isEmpty()) {
    			    expo[i] = Integer.parseInt(co_expo[1]);
    			} else {
    			    expo[i] = 1; 
    			}
    		}
    	}
    	
    	this.coefficients = co.clone();
        this.exponents = expo.clone();
    }

    public HashMap<Integer, Double> arrToMap() {
        HashMap<Integer, Double> dic = new HashMap<>();
        for (int i = 0; i < coefficients.length; i++) {
            dic.put(exponents[i], coefficients[i]);
        }
        return dic;
    }

    public Polynomial add(Polynomial po) {
        HashMap<Integer, Double> thisPo = arrToMap();
        HashMap<Integer, Double> newPo = po.arrToMap();

        for (Map.Entry<Integer, Double> entry : newPo.entrySet()) {
            thisPo.put(entry.getKey(), thisPo.getOrDefault(entry.getKey(), 0.0) + entry.getValue());
        }

        int[] expo = new int[thisPo.size()];
        double[] co = new double[thisPo.size()];

        TreeMap<Integer, Double> sortedDic = new TreeMap<>(thisPo);

        int i = 0;
        for (Map.Entry<Integer, Double> entry : sortedDic.entrySet()) {
            expo[i] = entry.getKey();
            co[i] = entry.getValue();
            i++;
        }

        return new Polynomial(co, expo);
    }

    public double evaluate(double arg) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(arg, exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double arg) {
        return evaluate(arg) == 0;
    }
    
    public Polynomial multiply(Polynomial po) {
    	HashMap<Integer, Double> thisPo = arrToMap();
        HashMap<Integer, Double> newPo = po.arrToMap();
        
        HashMap<Integer, Double> rePo = new HashMap<>();
        
        for (Map.Entry<Integer, Double> entry1 : newPo.entrySet()) {
        	for (Map.Entry<Integer, Double> entry2 : thisPo.entrySet()) {
        		int new_expo = entry1.getKey() + entry2.getKey();
        		double new_co = entry1.getValue() * entry2.getValue();;
        		rePo.put(new_expo, new_co + rePo.getOrDefault(new_expo, 0.0));
        	}
        }
        
        int[] expo = new int[rePo.size()];
        double[] co = new double[rePo.size()];
        int i = 0;
        
        for (Map.Entry<Integer, Double> entry : rePo.entrySet()) {
        	expo[i] = entry.getKey();
        	co[i] = entry.getValue();
        	i++;
        }
        
        return new Polynomial(co, expo);
    }
    
    public void saveToFile(String filename) throws IOException{
    	try (FileWriter writer = new FileWriter(filename)){
    		StringBuilder poly_str = new StringBuilder();
    		
    		poly_str.append(this.coefficients[0]);
    		
    		if (this.exponents[0] != 0) {
    			poly_str.append("x");
    			poly_str.append(this.exponents[0]);
    		}
    		
    		for (int i = 1; i < this.coefficients.length; i++) {
    			if (this.coefficients[i] > 0) {
    				poly_str.append("+");
    			}
    			else {
    				poly_str.append("-");
    			}
    			poly_str.append(Math.abs(this.coefficients[i]));
    			poly_str.append("x");
    			if (this.exponents[i] != 0) {
    				poly_str.append(this.exponents[i]);
    			}					
    		}
    		writer.write(poly_str.toString());
    	}
    	catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
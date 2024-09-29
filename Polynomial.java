package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Polynomials {
    int[] exponents;
    double[] coefficients;

    public Polynomials(){
        this.coefficients = new double[1];
        this.exponents = new int[1];
        coefficients[0] = 1;
        exponents[0] = 1;
    }

    public Polynomials(int[] exponents, double[] coefficients){
        this.coefficients = coefficients.clone();
        this.exponents = exponents.clone();
    }

    public Polynomials(File file) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(file);
        String target = fileScanner.nextLine();
        String[] targetArray = target.split("(?=[+-])");
        HashMap<Integer, Double> resultMap = new HashMap<>();
        for (String expression: targetArray) {
            if (expression.indexOf("x") != -1) {
                int indexX = expression.indexOf("x");

                Integer expo = (indexX + 1 < expression.length()) ?
                        Integer.parseInt(expression.substring(indexX + 1)) : 1;

                String coefficientStr = expression.substring(0, indexX);
                Double co;

                if (coefficientStr.equals("") || coefficientStr.equals("+")) {
                    co = 1.0;
                } else if (coefficientStr.equals("-")) {
                    co = -1.0;
                } else {
                    co = Double.parseDouble(coefficientStr);
                }

                resultMap.put(expo, co);
            }
            else {
                resultMap.put(0, Double.parseDouble(expression));
            }
        }
        int[] resultExponents = new int[resultMap.size()];
        double[] resultCoefficients = new double[resultMap.size()];
        int i = 0;
        for (Map.Entry<Integer, Double> entry: resultMap.entrySet()){
            resultExponents[i] = entry.getKey();
            resultCoefficients[i] = entry.getValue();
            i++;
        }

        this.coefficients = resultCoefficients.clone();
        this.exponents = resultExponents.clone();
    }

    public Polynomials mapToPolynomial(HashMap<Integer, Double> map){
        int[] exponents = new int[map.size()];
        double[] coefficients = new double[map.size()];
        int i = 0;
        for (Map.Entry<Integer, Double> entry : map.entrySet()){
            exponents[i] = entry.getKey();
            coefficients[i] = entry.getValue();
            i++;
        }
        return new Polynomials(exponents, coefficients);
    }



    public Polynomials add(Polynomials newPolynomials){
        HashMap<Integer, Double> resultMap = new HashMap<>();

        for (int i = 0; i < this.exponents.length; i++){
            resultMap.put(this.exponents[i], this.coefficients[i]);
        }

        for (int i = 0; i < newPolynomials.exponents.length; i++){
            if (resultMap.containsKey(newPolynomials.exponents[i])){
                double currValue = resultMap.get(newPolynomials.exponents[i]);
                resultMap.put(newPolynomials.exponents[i], currValue + newPolynomials.coefficients[i]);
            }
            else{
                resultMap.put(newPolynomials.exponents[i], newPolynomials.coefficients[i]);
            }
        }
        return mapToPolynomial(resultMap);
    }

    public double evaluate(double arg){
        double result = 0;
        for (int i = 0; i < this.exponents.length; i++){
            result += this.coefficients[i] * Math.pow(arg, this.exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double arg){
        return (this.evaluate(arg) == 0);
    }

    public Polynomials multiply(Polynomials newPolynomial){
        HashMap<Integer, Double> resultMap = new HashMap<>();
        for (int i = 0; i < this.exponents.length; i++){
            for (int j = 0; j < newPolynomial.exponents.length; j++){
                int currExponent = this.exponents[i] + newPolynomial.exponents[j];
                if (resultMap.containsKey(currExponent)){
                    double currCoefficient = resultMap.get(currExponent);
                    resultMap.put(currExponent, currCoefficient + this.coefficients[i] * newPolynomial.coefficients[j]);
                }
                else{
                    resultMap.put(currExponent, this.coefficients[i] * newPolynomial.coefficients[j]);
                }
            }
        }
        return mapToPolynomial(resultMap);
    }

    public String polynomialToString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < this.exponents.length; i++) {
            if (this.coefficients[i] != 0) {
                if (i > 0 && this.coefficients[i] > 0) {
                    result.append("+");
                }
                result.append(this.coefficients[i]);
                result.append("x");
                result.append(this.exponents[i]);
            }
        }

        return result.toString();
    }

    public void saveToFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        try (PrintWriter writer = new PrintWriter(file)) {
            String polynomialString = polynomialToString();
            if (polynomialString.isEmpty()) {
                writer.println("0");
            } else {
                writer.println(polynomialString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

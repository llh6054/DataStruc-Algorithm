package com.list;
import java.math.BigDecimal;

import com.list.MyArrayList;

public class StackApp {

	public static void main(String[] args) {
		
		StackApp stackApp = new StackApp();
		
		System.out.println(stackApp.reverse("Hello World"));
		System.out.println(stackApp.calculateReverPolish("6 5 2 3 + 8 * + 3 + *"));
	}
	
	/**
	 * reverse String
	 * 
	 * @param s
	 * @return the value of reverse String.
	 */
	public String reverse(String s) {
		
		String[] sArr = s.split("");
		StringBuilder sb = new StringBuilder();
		
		MyArrayList<String> stack = new MyArrayList<>();
		for (String x : sArr) {
			stack.push(x);
		}
		
		for(int i = 0; i < sArr.length - 1; i++)
			sb.append(stack.pop()) ;
			
		return sb.toString();
	}
	
	/**
	 * Calculate the value of Postfix Expressions
	 * 
	 * @param expression
	 * @param result of the Postfix Expressions. 
	 * */
	public BigDecimal calculateReverPolish(String expression) {
		
		String[] expArr = expression.split(" ");
		MyArrayList<String> stack = new MyArrayList<>();
		BigDecimal resultTemp = null;
		
		for(int i = 0; i < expArr.length; i++) {
			if(isOperator(expArr[i])) {
				
				BigDecimal x =  new BigDecimal(Double.parseDouble(stack.pop()));
				BigDecimal y =  new BigDecimal(Double.parseDouble(stack.pop()));
				
				switch(expArr[i]) {
					case "+": 	resultTemp = x.add(y);
						break;
					case "-":   resultTemp = x.subtract(y);
						break;
					case "*": 	resultTemp = x.multiply(y);
						break;
					case "/": 	resultTemp = x.divide(y);
				}	
				stack.push(String.valueOf(resultTemp.doubleValue()));
			}
			else {
				stack.push(expArr[i]);
			}	
				
		}
		
		return new BigDecimal(Double.parseDouble(stack.pop()));
	}
	
	/**
	 * if s is operator
	 * 
	 * @param s
	 * @return true if s is operator, vice verse.
	 */
	public boolean isOperator(String s) {
		return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
	}
	
	

}

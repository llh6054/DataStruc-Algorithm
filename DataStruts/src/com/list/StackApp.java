package com.list;
import java.math.BigDecimal;

import com.list.MyArrayList;

public class StackApp {
	
	private static final Integer leftCurPriority = 0;
	private static final Integer addSubPriority = 1;
	private static final Integer multiDivPriority = 2;
	private static final Integer rightCurPriority = 3;

	public static void main(String[] args) {
		
		StackApp stackApp = new StackApp();
		
		System.out.println(stackApp.reverse("Hello World"));
		System.out.println(stackApp.calculateReverPolish("6 5 2 3 + 8 * + 3 + *"));
		System.out.println(stackApp.infixToPostfix("a + b * c + ( d * e + f ) * g"));
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
	 */
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
	 * if opera is operator
	 * 
	 * @param s
	 * @return true if s is operator, vice verse.
	 */
	private boolean isOperator(String opera) {
		return opera.equals("+") || opera.equals("-") || opera.equals("*") || opera.equals("/") || opera.equals("(") || opera.equals(")");
	}
	
	/**
	 * Translate infix expressions to postfix
	 * 
	 * @param infix
	 * @return postfix expressions
	 */
	public String infixToPostfix(String infix) {
		StringBuilder postfix = new StringBuilder();
		MyArrayList<String> stack = new MyArrayList<>();
		int topOfStack = -1;
		
		String[] sArr = infix.split(" "); 
		
		for(String s : sArr) {
			
			if(isOperator(s)) {
				if(topOfStack == -1) {
					stack.push(s);
					topOfStack++;
					continue;
				}
					
					
				while(topOfStack != -1) {
					//if s is operator and has a higher priority, push it onto stack and break.
					if(!lowerPriority(s,stack.peek())) {
						if(s.equals(")")) { // a + has benn missed
							while(!stack.peek().equals("("))
								postfix.append(stack.pop());
								topOfStack--;
								System.out.println(postfix);
						}else {
							stack.push(s); 
							topOfStack++;
							break;
						}
						
					}
					//otherwise pop an element from stack and go on.
					else {
						if(!isParentheses(s)) {
							postfix.append(stack.pop());
							topOfStack--;
						}
							
						System.out.println(postfix.toString());
					}
						
				}
			}else {
				postfix.append(s);
				System.out.println(postfix.toString());
			}
		}
		
		while(topOfStack != -1) {
			postfix.append(stack.pop());
			topOfStack--;
		}
			
		return postfix.toString();
		
	}
	
	/**
	 * if operator is a parentheses.
	 * 
	 * @param operator
	 * @return true if operator is ( or )
	 */
	private boolean isParentheses(String operator) {
		return operator.equals("(") || operator.equals(")");
	}
	
	/**
	 * whether operator has a lower priority
	 * 
	 * @param operator
	 * @return true if operator has a lower priority to the element of top stack 
	 */
	private boolean lowerPriority(String opera1, String opera2) {
		return getPriority(opera1) <= getPriority(opera2);
	}
	
	/**
	 * Get the priority of operator
	 * 
	 * @param opera 
	 * @return priority
	 */
	private Integer getPriority(String opera) {
		Integer priority;
		switch(opera) {
			case "(": priority = leftCurPriority;
				break;
			case "+": priority = addSubPriority;
				break;
			case "-": priority = addSubPriority;
				break;
			case "*": priority = multiDivPriority;
				break;
			case "/": priority = multiDivPriority;
				break;
			case ")": priority = rightCurPriority;
				break;
			default : throw new IllegalArgumentException(opera);
		}
		return priority;
	}
}

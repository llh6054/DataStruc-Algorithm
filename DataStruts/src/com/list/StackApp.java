package com.list;
import java.math.BigDecimal;

import com.list.MyArrayList;
/**
 * 
 * @author chubby
 * 2017/3/28
 * 
 * 1.字符逆置
 * 2.后缀表达式求值
 * 3.中缀表达式转后缀表达式
 *
 */
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
		return opera.equals("+") || opera.equals("-") || opera.equals("*") || opera.equals("/");
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
				if(topOfStack == -1) {	//when operator stack is empty, pop an element
					stack.push(s);
					topOfStack++;
					continue;
				}
					
				//if stack is not empty and s not equals to  ( as well as priority of s is not higher to the element
				//of top of stack, pop an element until the condition is not matched.
				while(topOfStack != -1 && stack.peek() != "(" && getPriority(s) <= getPriority(stack.peek())) {
					postfix.append(stack.pop());	//append operator to postfix.
					topOfStack--;
				}
				//then push s onto stack. 
				stack.push(s);
				topOfStack++;
			}
			else if(s.equals("(")) {	//when s is ( push it onto stack.
				stack.push(s);
				topOfStack++;
			}
			else if(s.equals(")")) {	//when s is ) pop element until ( is matched
				while(!stack.peek().equals("(")) {
					postfix.append(stack.pop());	//operator is appended to postfix.
					topOfStack--;
				}
				stack.pop();	//pop a ( but not append to postfix.
				topOfStack--;
			}
			else {		//number is append to postfix.
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
	 * Get the priority of operator
	 * 
	 * @param opera 
	 * @return priority
	 */
	private Integer getPriority(String opera) {
		
		if(!isOperator(opera) && !opera.equals("(") && !opera.equals(")"))
			throw new IllegalArgumentException(opera);
		
		Integer priority = -1;
		
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
		}
		return priority;
	}
}

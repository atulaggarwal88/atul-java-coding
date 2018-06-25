/*
 * 7. Factorial (solution)
This is one of the simplest programs you can expect on interviews. It is generally asked to see if you can code 
or not. Sometimes interviewer may also ask about changing a recursive solution to iterative one or vice-versa.

 */

package com.interview.coding;

public class TestFactorial {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		System.out.println(getFactorial_Iterative(10));
		System.out.println(getFactorial_Recursive(10));
	}
	public static int getFactorial_Iterative(int number){
		//		10 = 10 * 9 * 8 * 7 * 6..
		int result = 1;
		for(int i=1; i<=number; i++){
			result = result * i;
		}		
		return result;
	}
	public static int getFactorial_Recursive(int number){
		if(number==1)		
			return 1;
		else{
			/*int result = number;
			--number;
			return result * getFactorial_Recursive(number);*/
			
			return number * getFactorial_Recursive(--number);
		} 
	}

}

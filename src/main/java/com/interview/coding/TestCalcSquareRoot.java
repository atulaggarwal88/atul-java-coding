/*You need to write a program to calculate the square root of a number without using 
 * the Math.sqrt() function of JDK. You need to write your logic and method to 
 * calculate the square root. You can though use popular algorithm e.g. Newton's method.
 */
package com.interview.coding;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestCalcSquareRoot {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Square root of 3025 is: " + getSquareRootOfInteger(3025));
		System.out.println("Square root of 3025 is: " + getSquareRootOfInteger(150625));
	}

	static double getSquareRootOfInteger(int i){
		List<String> grpTwoDigitList = getTwoDigitGroups_v2(i);
		//		System.out.println(grpTwoDigitList);
		int dividend = 0;
		int divisor = 0;
		double quotient = 0;
		int remainder = 0;
		int result = 0;
		int currentSze = grpTwoDigitList.size();
		int precision = 3;
		while(precision-- > 0){
			grpTwoDigitList.add("00");
		}
		int precisionDenom = 1;

		for(int j=0; j<grpTwoDigitList.size(); j++){		
			dividend = Integer.valueOf("" + remainder + grpTwoDigitList.get(j));
			divisor = getDivisorFor(dividend, divisor);			
			int tempQuotient = divisor%10;
			if(j<currentSze){
				quotient = quotient*10 + tempQuotient;
			}else{
				precisionDenom *= 10;
				quotient = quotient + (double)tempQuotient/precisionDenom;
			}
			remainder = dividend -(divisor * (divisor%10));			
			divisor += tempQuotient;			
		}		
		return quotient;
	}
	static List<Integer> getTwoDigitGroups(int i){
		List<Integer> list1 = new ArrayList<>();
		int temp = i;
		int last2Digit = 0;
		while(temp != 0){
			last2Digit = temp%100;
			list1.add(last2Digit);			
			temp = temp/100;
		}
		int size = list1.size() - 1;

		ArrayList<Integer> list2 = IntStream.
				rangeClosed(0, size).
				mapToObj(x -> list1.get(size-x)).
				collect(Collectors.toCollection(ArrayList::new));		
		return list2;
	}
	static List<String> getTwoDigitGroups_v2(Integer i){
		List<String> list1 = new ArrayList<>();
		StringBuilder temp = new StringBuilder(String.valueOf(i));
		String last2Digit = "";
		try{
			while(temp.length() != 0){
				last2Digit = temp.substring(temp.length()-2);
				list1.add(last2Digit);

				temp = new StringBuilder(temp.substring(0, temp.length()-2));			
			}
		}catch(StringIndexOutOfBoundsException e){
			last2Digit = temp.substring(0);			
			list1.add("0" + last2Digit);
		}		
		int size = list1.size() - 1;

		ArrayList<String> list2 = IntStream.
				rangeClosed(0, size).
				mapToObj(x -> list1.get(size-x)).
				collect(Collectors.toCollection(ArrayList::new));		
		return list2;
	}
	static int getDivisorFor(int dividend, int currentDivisor){
		int newDivisor = 0;
		if(currentDivisor==0){
			OptionalInt optInt = IntStream.
					rangeClosed(1, 9).
					filter(x -> dividend < (x*x)).
					map(x -> x-1).
					findAny();
			newDivisor = optInt.getAsInt();			
		}else{
			OptionalInt optInt = IntStream.
					rangeClosed(0, 9).
					filter(x -> dividend < (Integer.valueOf("" + currentDivisor + x)*x)).
					map(x -> x-1).
					map(x -> Integer.valueOf("" + currentDivisor + x)).
					findAny();
			newDivisor = optInt.getAsInt();	
		}			
		return newDivisor;
	}

}

package com.interview.coding;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TesFindGCDOf2Number {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a = 20;
		int b = 10;
		System.out.printf("GCD of number %d and %d is: %d\n", a, b, findGCDOfNumber(a, b));
		System.out.printf("GCD of number %d and %d is: %d\n", a, b, findGCDOfNumber_Recursive(a, b));
	}

	static int findGCDOfNumber(int x, int y){
		List<Integer> l1 = findFactors(x);
		List<Integer> l2 = findFactors(y);
		List<Integer> commonFact = new ArrayList<>();
		l1.stream().filter(i -> l2.contains(i)).forEach(commonFact::add);
		Optional<Integer> opt = commonFact.stream().max(Integer::compare);		
		return opt.get();
	}
	static List<Integer> findFactors(int x){
		int sqrt = (int)Math.sqrt(x);
		List<Integer> list = new ArrayList<>();
		for(int i=1; i<=sqrt; i++){
			if(x % i == 0){ 
				list.add(i);
				list.add(x/i);
			}
		}		
		System.out.println("Factors are: " + list.toString());
		return list;
	}
	
	
	static int findGCDOfNumber_Recursive(int x, int y){
		if(y==0){
			return x;
		}
		/*below recrsion uses Euclids theorem*/
		return findGCDOfNumber_Recursive(y, x%y);
	}
	
}

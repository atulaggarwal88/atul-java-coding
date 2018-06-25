/*Write a simple Java program which will print Fibonacci series e.g. 1 1 2 3 5 8 13 ... . up to a given number. 
 * Be prepare for cross questions like using iteration over recursion and how to optimize the solution using 
 * caching and memorization. *
 *
 */
package com.interview.coding;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TestFibonacci {
	static int counter = 0;
	static Map<Integer, Integer> map = new HashMap<>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a number: ");
		Integer input = scan.nextInt();
		System.out.println("Print Fibonnacii number:");
		long startMilli = System.currentTimeMillis();
		for(int i=1; i<=input; i++){
			//			System.out.print(getFibUsingIteration2(i) + " ");
			//			System.out.print(getFibUsingRecursion(i) + " ");
//			System.out.print(getFibUsingMemory(i) + " ");
			System.out.print(improvedFibo(i) + " ");
		}		
		//		printFibUsingIteration(input);
		long endMilli = System.currentTimeMillis();
		System.out.println("\n Total time: " + (float)(endMilli-startMilli)/1000);
	}
	public static void printFibUsingIteration(int i){
		int firstNo = 1;
		int secondNo = 1;
		//		int nextNo = 0;
		System.out.print(firstNo + " " + secondNo);
		for(int nextNo = 0; nextNo<i; ){			
			nextNo = secondNo + firstNo;
			firstNo = secondNo;
			secondNo = nextNo;
			System.out.print(" " + nextNo);
		}
	}

	//	1 	1 2 3 5 8 13 21 34 55
	//	f1=1; f2=1;	f3=2; f4=3; f5=5; f6=8; f7=13; f8=21; f9=34;
	//	f3=f1+f2; f4=f3+f2; f5=f4+f3
	public static int getFibUsingRecursion(int i){
		if(i==1 || i==2){
			return 1;			
		}		
		return getFibUsingRecursion(i-2) + getFibUsingRecursion(i-1);
	}
	
	//my method
	public static int getFibUsingMemory(int i){
		if(i==1 || i==2){			
			map.put(i,getFibUsingIteration2(i));			
			return map.get(i);
		}
		int fib3 = map.get(i-1) + map.get(i-2);
		map.put(i, fib3);
		return map.get(i);
	}

	public static int getFibUsingIteration2(int i){
		if(i==1 || i==2){
			return 1;			
		}
		int fib1 = 1, fib2 = 1, fib3 = 0;
		for(int y=3; y<=i; y++){
			fib3 = fib1 + fib2;
			fib1 = fib2;
			fib2 = fib3;			
		}
		return fib3;		
	}
	
	//taken from java67
	public static int improvedFibo(int number){ 
		Integer fibonacci = map.get(number); 
		if(fibonacci != null){ 
			return fibonacci; //fibonacci number from cache 
		} 

		//fibonacci number not in cache, calculating it 
		fibonacci = getFibUsingIteration2(number);

		//putting fibonacci number in cache for future request 
		map.put(number, fibonacci); 
		return fibonacci; 
	}
}






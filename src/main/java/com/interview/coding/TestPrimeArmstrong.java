/*
 * Write a Java program to check if a given number is prime or not. Remember, a prime number is a number which 
is not divisible by any other number e.g. 3, 5, 7, 11, 13, 17 etc. 
Be prepared for cross e.g. checking till the square root of a number etc.
*/
package com.interview.coding;

import java.util.Scanner;

public class TestPrimeArmstrong {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a number");
		int i = scan.nextInt();
		System.out.printf("Is %d a prime number: %b \n",i,isPrimeNumber(i));
		System.out.printf("Is %d an Armstrong number: %b \n",i,isArmstrongNumber(i));

	}
	
	//atul
	public static boolean isPrimeNumber(int x){
		long sqrt = Math.round(Math.sqrt(x));
		
		if(x%2==0) return false;//check if divisible by 2
		
		for(int i=1; i<=sqrt; i+=2){//increment counter by 2 as number is not an even number
			if(x%i==0) return false;
		}		
		return true;
	}
	//atul
	public static boolean isArmstrongNumber(int x){		
		
		int sum = 0;
		int temp = x;
		int len=0;
		
		//calculate total no of digits
		while(temp>0){
			++len;
			temp = temp/10;
		}
		temp = x;
		
		while(temp>0){
			//Get last digit
			int c = temp%10;
			
			//To calculate power of a number
			int prod = 1;			
			for(int j=0; j<len; j++){
				prod *= c;				
			}			
			temp = temp/10;
			sum += prod;			
		}		
		if(x==sum) return true;
		return false;
	}	
}

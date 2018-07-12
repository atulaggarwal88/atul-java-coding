/*Print Pyramid using Java pattern.*/

package com.interview.coding;

public class TestPatternPrinting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		printPyramid(5);
	}
	static void printPyramid(int i){
		for(int x=0; x<i; x++){
			for(int z=0; z<i-x-1; z++){
				System.out.print(" ");
			}			
			for(int y=0; y<=x; y++){
				System.out.print("* ");
			}
			System.out.println();

		}
	}

}

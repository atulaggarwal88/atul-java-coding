/*Reverse an array in-place in Java*/
/*Reverse an sentence in Java*/
package com.interview.coding;

import java.util.Arrays;

public class TestReverseArrayInPlace {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sentence = "My name is atul";
		String[] strArry = sentence.split("\\s");
		System.out.println(Arrays.toString(strArry));

		//		reverseArray(strArry);
		reverseArray_recursively(strArry, 0, strArry.length-1);
		System.out.println(Arrays.toString(strArry));
		
		System.out.println("reversed sentence is: " + arrayToString(strArry));
	}

	static String arrayToString(String[] ogStr){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<ogStr.length; i++){
			sb.append(ogStr[i]);
			sb.append(" ");
		}
		return sb.toString();
	}
	static void reverseArray(String[] ogArry){
		if(ogArry == null || ogArry.length<2){
			return;
		}
		for(int i=0; i<ogArry.length/2; i++){
			String temp = ogArry[i];
			ogArry[i] = ogArry[ogArry.length-1-i];
			ogArry[ogArry.length-1-i] = temp;
		}
	}
	static void reverseArray_recursively(String[] ogArry, int startIndx, int endIndx){
		if(startIndx >= endIndx){
			return;
		}
		String temp = ogArry[endIndx];
		ogArry[endIndx] = ogArry[startIndx];
		ogArry[startIndx] = temp;
		startIndx++;
		endIndx--;
		reverseArray_recursively(ogArry, startIndx, endIndx);
	}

}

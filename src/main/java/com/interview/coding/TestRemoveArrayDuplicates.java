/*9. Remove duplicates from array (solution)
Write a program to remove duplicates from an array in Java without using the Java Collection API. 
The array can be an array of String, Integer or Character, your solution should be independent of the type of array. 
If you want to practice more array based questions then see this list of top 30 array interview questions from 
Java interviews.*/

package com.interview.coding;

import java.util.Arrays;

public class TestRemoveArrayDuplicates {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] i = {3,3,5,7,3};
		String[] s = {"abc", "abc", "abc", "pqr", "abc", "abc"};
		Character[] c = {'1', '1', 's', 'd', 'q', 'a', '1', '2', 's', 'd', 'q', 'a'};
		System.out.println("Original Array: " + Arrays.toString(s));
		System.out.println("Original Array: " + Arrays.toString(i));
		System.out.println("Original Array: " + Arrays.toString(c));
		
		System.out.println("After removing duplicates");
		System.out.println(Arrays.toString(getDistinctArray_withoutAPI(s)));
		System.out.println(Arrays.toString(getDistinctArray_withoutAPI(i)));
		System.out.println(Arrays.toString(getDistinctArray_withoutAPI(c)));
		//		getDistinctArray_withoutAPI(i);

	}
	static public String[] getDistinctArray_withoutAPI(Object[] objArry){
		String[] str = new String[objArry.length];
		int i = 0;
		for(Object obj:objArry){
			str[i++] = obj.toString();

			/*if(obj instanceof String){

				System.out.println("string");
			}else if(obj instanceof Integer){
				System.out.println("Integer");
			}else if(obj instanceof Character){
				System.out.println("Character");
			}*/
		}
		str = getDistinctStringArray(str);
		return str;
	}
	public static String[] getDistinctStringArray(String[] strArry){
		String[] newStrArry = new String[strArry.length];		
		int z=0;

		for(int j=0; j<strArry.length; ++j){
			int matches = 0;			
			for(int i=0; i<strArry.length; i++){						
				if(strArry[j].equalsIgnoreCase(strArry[i])){
					++matches;
					if(matches > 1) {						
						if(j>=i) {
							newStrArry[z] = null;
							--z;
						}
						break;
					}
					else
						newStrArry[z] = strArry[i];
				}
			}
			++z;

		}
		return newStrArry;
	}

}
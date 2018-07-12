/*Binary search*/
package com.interview.coding;

public class TestBinarySearch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] intArry = {1,2,3,4,5,6,9,11,16,19};
		int result = binarySearch_Recursive(intArry, 50);
		System.out.println(result==-1 ? ("result not found") : ("result found at index: " + result));
		
		result = binarySearch_Recursive(intArry, 16);
		System.out.println(result==-1 ? ("result not found") : ("result found at index: " + result));

	}
	static int binarySearch_Recursive(int[] intArry, int key){
		int result = search_Intm(intArry, key, 0, intArry.length-1);
		return result;		
	}

	static int search_Intm(int[] intArry, int key, int start, int end){
		int middle = (end+start)/2;
		
		if(start <= end){
			if(key == intArry[middle])
				return middle;
			else if(key < intArry[middle]){
				return search_Intm(intArry, key, start, middle-1);
			}
			else if(key > intArry[middle]){
				return search_Intm(intArry, key, middle+1, end);
			}
		}else{
			return -1;
		}
		return -1;
	}

}

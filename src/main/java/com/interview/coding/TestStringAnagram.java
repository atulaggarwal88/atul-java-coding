/*Write a program to check if two given String is Anagram of each other. 
Your function should return true if two Strings are Anagram, false otherwise. 
A string is said to be an anagram if it contains same characters and same length but 
in different order e.g. army and Mary are anagrams. You can ignore cases for this problem but 
you should clarify that from your interview.*/
package com.interview.coding;

import java.util.Arrays;

public class TestStringAnagram {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Is given String an anagram? " + ifStringAnagram("abcd", "acbd"));
		System.out.println("Is given String an anagram? " + ifStringAnagram("abcd", "adcbd"));
	}
	
	static boolean ifStringAnagram(String str1, String str2){
		char[] c1 = str1.toCharArray();
		char[] c2 = str2.toCharArray();
		Arrays.sort(c1);
		Arrays.sort(c2);
		return Arrays.equals(c1, c2);
	}

}

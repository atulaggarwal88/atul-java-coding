/*
 * Q1:
 * You need to write a simple Java program to check if a given String is palindrome or not. 
A Palindrome is a String which is equal to the reverse of itself e.g. "Bob" is a palindrome 
because of the reverse of "Bob" is also "Bob".  Though be prepared with both recursive and iterative solution 
of this problem. 
The interviewer may ask you to solve without using any library method e.g. indexOf() or subString() 
so be prepared for that.

 *
 */

/*Notes: 
1.reverse function in String builder reverses the original string builder
2. equals method of StringBuilder does not compare contents. 
 *
 *
 */

package com.interview.coding;

import java.util.Arrays;

public class TestPalindrome {

	public static void main(String[] args) {		
//		System.out.println("Is given string a palindrome?: " + isStringPalindrome_StrBuilder("Bob123"));
//		System.out.println("Is given string a palindrome?: " + isStringPalindrome_StrBuilder("Bob"));
//		
//		System.out.println("Is given string a palindrome?: " + isStringPalindrome_Iterative("Bob123"));
//		System.out.println("Is given string a palindrome?: " + isStringPalindrome_Iterative("Bob"));
//		
//		System.out.println("Is given string a palindrome?: " + isStringPalindrome_Recursive("Bob123"));
//		System.out.println("Is given string a palindrome?: " + isStringPalindrome_Recursive("Bob"));
//		System.out.println("Is given string a palindrome?: " + isStringPalindrome_Recursive("Bobbob"));
		
		System.out.println("Is given Integer a palindrome?: " + isIntegerPalindrome_Iterative(123));
		System.out.println("Is given Integer a palindrome?: " + isIntegerPalindrome_Iterative(123321));
		System.out.println("Is given Integer a palindrome?: " + isIntegerPalindrome_Iterative(0));

	}
	public static boolean isStringPalindrome_Iterative(String str){
		str = str.toLowerCase();
		char[] og_charAry = str.toCharArray();
		char[] rev_charAry = new char[og_charAry.length];
		for(int i = 0; i<og_charAry.length; i++){
			rev_charAry[i] = og_charAry[og_charAry.length-1-i];
		}
		//		System.out.println(Arrays.toString(og_charAry));
		//		System.out.println(Arrays.toString(rev_charAry));
		return Arrays.equals(og_charAry, rev_charAry);
	}
	public static boolean isStringPalindrome_StrBuilder(String str){
		//		str = str.toLowerCase();
		StringBuilder strBuildr = new StringBuilder(str);
		String og_Str = strBuildr.toString();
		String rev_Str = strBuildr.reverse().toString();
		return og_Str.equalsIgnoreCase(rev_Str);
		
/*		String rev_Str = new StringBuilder(str).reverse().toString();
		return str.equalsIgnoreCase(rev_Str);*/		
	}
	public static boolean isStringPalindrome_Recursive(String str){
		//odd no of char - middle index and boolean = true
		//even no of char - index before middle and boolean = true		
		try{
			str = str.toLowerCase();
			char c1 = str.charAt(0);
			char c2 = str.charAt(str.length()-1);

			if(c1 == c2){
				str = str.substring(1, str.length());
				isStringPalindrome_Recursive(str);
			}else{		
				return false;
			}
		}catch(Exception e){
//			return true;
		}
		return true;
	}
	public static boolean isIntegerPalindrome_Iterative(int number){
		int palindrome = number;
		int rev_Integer = 0;
		while(palindrome != 0){
			int remainder = palindrome % 10;
			rev_Integer = rev_Integer*10 + remainder;
			palindrome = palindrome/10;
		}
		return (number==rev_Integer) ? true:false;
	}

}

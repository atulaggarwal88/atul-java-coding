package com.interview.coding;

import java.time.Year;

public class TestLeapYear {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("is 2012 a leap year " + isLeapYear(2012));
		System.out.println("is 2014 a leap year " + isLeapYear(2014));

	}
	static boolean isLeapYear(int yearInt){
		Year yr = Year.of(yearInt);
		return yr.isLeap();		
	}

}

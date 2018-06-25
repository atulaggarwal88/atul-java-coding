package com.interview.coding;

public class TestCalculatePower {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(powerRecursion(45,2));
		System.out.println(powerRecursion(9,4));
		System.out.println(powerRecursion(9,3));

	}
	/* Function to calculate x raised to the
    power y */
	static int powerRecursion(int x, long y)
	{
		if( y == 0)
			return 1;
		if (y%2 == 0)
			return powerRecursion(x, y/2)*powerRecursion(x, y/2);
		return x*powerRecursion(x, y/2)*powerRecursion(x, y/2);
	}

}

/*Print repeated character of String*/
package com.interview.coding;

import java.util.HashMap;
import java.util.Map;

public class TestFindDuplicateCharInString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		findCharacterOccurencesInString("jaJva");
	}
	static void findCharacterOccurencesInString(String str){
		Map<Character, Integer> map = new HashMap<>();
		for(int i=0; i<str.length(); i++){
			Character c = str.toLowerCase().charAt(i);
			if(map.containsKey(c)){
				int val = map.get(c);
				++val;
				map.put(c, val);
			}else{
				map.put(c, 1);
			}
		}
		System.out.println(map);
	}

}

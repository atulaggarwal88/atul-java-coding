package pack_collections;

import java.util.*;

public class PracticeSetCollection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createTreeSet();
	}
	public static void createHashSet(){
		Set<String> set = new HashSet<>();
		set.add("4");
		set.add("2");
		set.add("3");
		set.add("2");
		System.out.println(set);
		printSet(set);
	}
	public static void printSet(Set set){
		Iterator<String> iter = set.iterator();
	
		while(iter.hasNext()){
			System.out.println(iter.next());
		}
		
	}
	public static void createTreeSet(){
		Set<String> set = new TreeSet<>();
		set.add("4");
		set.add("2");
		set.add("3");
		set.add("2");
		System.out.println(set);
		printSet(set);
		
	}

}

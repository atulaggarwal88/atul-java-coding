package pack_collections;
import java.util.*;

public class PracticeHashMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createHashMap();
		
	}
	public static void createHashMap(){
		Map<String, String> map = new HashMap();
		map.put("2","Atul");
		map.put("1","Jay");
		map.put("3","Bharti");
		printMap(map);		
	}
	public static void printMap(Map map){
		Set set = map.keySet();
		//Map.Entry<String, String> entry = map.entrySet();
		Iterator iter = set.iterator();
		while(iter.hasNext()){
			System.out.println(iter.next());
		}
		
	}

}

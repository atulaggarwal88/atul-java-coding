package pack_filereading;

import java.io.*;
public class PracticeTextFileReading {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String path = System.getProperty("user.dir") + "\\test.txt";
		File file = new File(path);
		FileReader fr = new FileReader(file);
		BufferedReader bfr = new BufferedReader(fr);
		String str  = bfr.readLine();
		
		while(str != null){
			System.out.println(str);
			str = bfr.readLine();
		}

	}

}

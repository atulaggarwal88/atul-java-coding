package pack_filereading;

import java.util.Properties;
import java.io.*;

public class PracticeReadingPropertyFile {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String path = System.getProperty("user.dir") + "\\env.properties";
		Properties prop = new Properties();
		prop.load(new FileReader(path));
		System.out.println(prop.getProperty("browser"));
	}

}

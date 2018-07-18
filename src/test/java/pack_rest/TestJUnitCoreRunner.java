//Main method for running JUnit test cases from JUnit core
package pack_rest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.listeners.CustomJunitListener;


public class TestJUnitCoreRunner{
	//	private final RunNotifier fNotifier = new RunNotifier();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JUnitCore jCore = new JUnitCore();
		
		/*Uncomment below only we are not using Custom runner i.e. @RunWith(CustomJUnitRunner.class)
		else listener will be run twice since listener is already registered in custom runner*/
//				jCore.addListener(new CustomJunitListener());

		Result result = jCore.run(TestWebServiceRestAssured_Json_Junit.class);			
		System.out.println("Result = " +result.wasSuccessful());
	}	
}

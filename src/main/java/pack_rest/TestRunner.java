package pack_rest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Result result = JUnitCore.runClasses(TestWebServiceRestAssured_Json_Junit.class);					
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.getTrace());					
		}		
		System.out.println("Result = " +result.wasSuccessful());

	}

}

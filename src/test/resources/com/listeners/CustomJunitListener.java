//Custom JUnit listener
package com.listeners;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class CustomJunitListener extends RunListener{

	public void testStarted(Description description) throws Exception {
		System.out.println(description.getMethodName() + " has started");
	}

	public void testFinished(Description description) throws Exception {
		System.out.println(description.getMethodName() + " is finished");
	}

	public void testFailure(Failure failure) throws Exception {
		System.out.println(failure.getDescription().getMethodName() + " is failed");
	} 
}

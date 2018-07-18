//My custom JUnit runner
package com.runner;

import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import com.listeners.CustomJunitListener;

public class CustomJUnitRunner extends BlockJUnit4ClassRunner{
	public CustomJUnitRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}
	
	@Override
	public void run(final RunNotifier notifier) {
		System.out.println("Executing run method of custom runner");
		
		//Added listener to notifier object
		notifier.addListener(new CustomJunitListener());

		EachTestNotifier testNotifier = new EachTestNotifier(notifier, getDescription());
		try {
			Statement statement = classBlock(notifier);
			statement.evaluate();
		} catch (AssumptionViolatedException e) {
			testNotifier.fireTestIgnored();
		} catch (StoppedByUserException e) {
			throw e;
		} catch (Throwable e) {
			testNotifier.addFailure(e);
		}
	}
}
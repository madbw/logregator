package com.century.logregator.dao;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class DbUnitRunner extends SpringJUnit4ClassRunner {
    /**
     * Constructs a new {@code SpringJUnit4ClassRunner} and initializes a
     * {@link TestContextManager} to provide Spring testing functionality to
     * standard JUnit tests.
     *
     * @param clazz the test class to be run
     * @see #createTestContextManager(Class)
     */
    public DbUnitRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }


    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        System.out.println("invoik" + method.getName());
        return super.methodInvoker(method, test);
    }
}

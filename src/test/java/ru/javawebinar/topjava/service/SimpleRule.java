package ru.javawebinar.topjava.service;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;


public class SimpleRule implements MethodRule {

    @Override
    public Statement apply(Statement statement, FrameworkMethod frameworkMethod, Object o) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                System.out.println("before");
                long startTime = System.currentTimeMillis();
                try {
                    statement.evaluate();
                } finally {
                    long stopTime = System.currentTimeMillis();
                    long elapsedTime = stopTime - startTime;
                    System.out.println(frameworkMethod.getName() + " " + elapsedTime + " ms");
                    System.out.println("after");
                }
            }
        };
    }
}

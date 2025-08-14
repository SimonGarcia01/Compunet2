package org.example.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppContext {

    // Holds the single instance
    private static AppContext instance;

    // The actual Spring application context
    final private ApplicationContext applicationContext;

    // Private constructor to prevent direct instantiation
    private AppContext() {
        applicationContext = new ClassPathXmlApplicationContext("context.xml");
    }

    // Lazily creates the instance when needed
    public static synchronized AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    // Access the Spring ApplicationContext
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}


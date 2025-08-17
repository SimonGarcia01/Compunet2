package org.example.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppContext {

    private static AppContext instance;

    final private ApplicationContext applicationContext;

    private AppContext() {
        applicationContext = new ClassPathXmlApplicationContext("context.xml");
    }

    public static synchronized AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
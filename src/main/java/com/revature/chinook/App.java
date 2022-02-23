package com.revature.chinook;

import org.apache.catalina.LifecycleException;

public class App {
    public static void main(String[] args) {
        AppContext.build();

        // Serve on Tomcat server
        try {
            AppContext.getTomcat().start();
        } catch (LifecycleException e) {
            System.err.println("Embedded server failed to start!");
        }

        // Save results
    }
}

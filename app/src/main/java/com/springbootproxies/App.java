package com.springbootproxies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.lang.reflect.Proxy;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        // Start Spring Boot (optional - for web features)
        SpringApplication.run(App.class, args);

        // Dynamic Proxy Demo
        System.out.println("=== DYNAMIC PROXY DEMO ===");

        // Create original object
        Man mohan = new Man("Mohan", 30, "Delhi", "India");

        // Get class loader and interfaces
        ClassLoader mohanClassLoader = mohan.getClass().getClassLoader();
        Class<?>[] interfaces = mohan.getClass().getInterfaces();

        // Create dynamic proxy
        Person proxyMohan = (Person) Proxy.newProxyInstance(
                mohanClassLoader,
                interfaces,
                new PersonInvocationHandler(mohan)
        );

        // Test proxy calls
        System.out.println("\n--- PROXY CALLS ---");
        proxyMohan.introduce("test");
        proxyMohan.sayAge("test");
        proxyMohan.sayWhereFrom("test", "test");

        System.out.println("\n--- DIRECT CALLS (NO PROXY) ---");
        mohan.introduce("direct");
        mohan.sayAge("direct");
        mohan.sayWhereFrom("direct", "direct");

        System.out.println("\n=== DEMO COMPLETED ===");
    }
}
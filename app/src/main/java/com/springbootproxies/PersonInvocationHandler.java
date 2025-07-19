package com.springbootproxies;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PersonInvocationHandler implements InvocationHandler {
    private Person person;

    public PersonInvocationHandler(Person person) {
        this.person = person;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("=== PROXY INTERCEPTED ===");
        System.out.println("Method called: " + method.getName());
        System.out.println("Before method execution");

        // Call the actual method
        Object result = method.invoke(person, args);

        System.out.println("After method execution");
        System.out.println("=== END PROXY ===");

        return result;
    }
}
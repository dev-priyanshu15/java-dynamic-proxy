# 🚀 Spring Boot Dynamic Proxy 

A **powerful demonstration** of Java Dynamic Proxies with Spring Boot! This project showcases how to intercept and modify object behavior at runtime without changing the original code.

## 📋 Table of Contents
- [What is Dynamic Proxy?](#what-is-dynamic-proxy)
- [Project Structure](#project-structure)
- [Code Explanation](#code-explanation)
- [How to Run](#how-to-run)
- [Output Explanation](#output-explanation)
- [Real-World Use Cases](#real-world-use-cases)

## 🤔 What is Dynamic Proxy?

**Dynamic Proxy** is a design pattern that intercepts method calls at runtime. It's especially useful when you need to:

- **Add logging** without modifying original code
- **Implement security checks**
- **Monitor performance**
- **Add caching mechanisms**
- **Handle cross-cutting concerns**

### Proxy vs Original Object
```
Client → Proxy Object → Original Object
         ↑ (Intercepts all method calls)
```

## 📁 Project Structure

```
springBootProxies/
├── app/
│   ├── src/main/java/com/springbootproxies/
│   │   ├── App.java                    # Main Spring Boot application
│   │   ├── Person.java                 # Interface defining the contract
│   │   ├── Man.java                    # Concrete implementation
│   │   └── PersonInvocationHandler.java # The proxy brain!
│   └── build.gradle                    # Dependencies and configuration
└── README.md
```

## 💻 Code Explanation

### 1. **Person Interface** - The Contract
```java
public interface Person {
    void introduce(String name);
    void sayAge(String age);
    void sayWhereFrom(String city, String country);
}
```
**Purpose:** This interface defines the contract that any Person implementation must follow. The proxy can only intercept methods defined in interfaces.

### 2. **Man Class** - The Real Implementation
```java
public class Man implements Person {
    private String name;
    private int age;
    private String city;
    private String country;
    
    // Constructor and actual method implementations
    @Override
    public void introduce(String name) {
        System.out.println("My name is " + this.name);
    }
    // ... other methods
}
```
**Purpose:** This is the actual implementation of the Person interface. It contains real data and provides the actual behavior that we want to proxy.

### 3. **PersonInvocationHandler** - The Magic! ✨
```java
public class PersonInvocationHandler implements InvocationHandler {
    private Person person;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("=== PROXY INTERCEPTED ===");
        System.out.println("Method called: " + method.getName());
        System.out.println("Before method execution");

        // Call the actual method on the real object
        Object result = method.invoke(person, args);

        System.out.println("After method execution");
        System.out.println("=== END PROXY ===");
        return result;
    }
}
```
**Purpose:** 
- Intercepts every method call made to the proxy
- Adds logging before and after method execution
- Delegates the actual call to the real object
- Returns the result from the original method

### 4. **App Class** - Where the Magic Happens
```java
// Create the original object
Man mohan = new Man("Mohan", 30, "Delhi", "India");

// Create the dynamic proxy
Person proxyMohan = (Person) Proxy.newProxyInstance(
    mohan.getClass().getClassLoader(),
    mohan.getClass().getInterfaces(),
    new PersonInvocationHandler(mohan)
);

// Use the proxy - calls are automatically intercepted!
proxyMohan.introduce("test");
```

**Key Components:**
- **ClassLoader**: Loads the proxy class
- **Interfaces**: Array of interfaces the proxy should implement
- **InvocationHandler**: Handles all method calls

## 🔧 How to Run

### Prerequisites
- **Java 17+** installed
- **Gradle** installed (or use the Gradle wrapper)

### Steps
1. **Clone the repository:**
   ```bash
   git clone <your-repo-url>
   cd springBootProxies
   ```

2. **Run the application:**
   ```bash
   ./gradlew bootRun
   ```
   Or alternatively:
   ```bash
   ./gradlew clean run
   ```

3. **Stop the application:**
   ```
   Press Ctrl+C
   ```

## 📊 Output Explanation

When you run the application, you'll see this output:

```
=== DYNAMIC PROXY DEMO ===

--- PROXY CALLS ---
=== PROXY INTERCEPTED ===
Method called: introduce
Before method execution
My name is Mohan
After method execution
=== END PROXY ===

=== PROXY INTERCEPTED ===
Method called: sayAge
Before method execution
I am 30 years old
After method execution
=== END PROXY ===

--- DIRECT CALLS (NO PROXY) ---
My name is Mohan
I am 30 years old
I'm from Delhi, India

=== DEMO COMPLETED ===
```

### Output Analysis:
- **Proxy calls** show logging around each method execution
- **Direct calls** only execute the original method
- This clearly demonstrates the proxy's interception behavior

## 🌟 Key Concepts Explained

### 1. **Reflection API**
```java
Method method = person.getClass().getMethod("introduce", String.class);
method.invoke(person, "test");
```
Allows runtime access to method information and dynamic method invocation.

### 2. **Proxy.newProxyInstance()**
```java
Proxy.newProxyInstance(classLoader, interfaces, invocationHandler)
```
Creates a dynamic proxy object that implements the specified interfaces.

### 3. **InvocationHandler**
The central point where all method calls are routed - provides a single place to handle cross-cutting concerns.

## 🎯 Real-World Use Cases

### 1. **Logging Framework**
```java
// Before method execution
logger.info("Calling method: " + method.getName());
Object result = method.invoke(target, args);
// After method execution
logger.info("Method completed successfully");
```

### 2. **Security/Authentication**
```java
// Check if user has permission
if (!hasPermission(method)) {
    throw new SecurityException("Access denied!");
}
return method.invoke(target, args);
```

### 3. **Performance Monitoring**
```java
long startTime = System.currentTimeMillis();
Object result = method.invoke(target, args);
long endTime = System.currentTimeMillis();
System.out.println("Execution time: " + (endTime - startTime) + "ms");
```

### 4. **Caching**
```java
String cacheKey = method.getName() + Arrays.toString(args);
if (cache.containsKey(cacheKey)) {
    return cache.get(cacheKey);
}
Object result = method.invoke(target, args);
cache.put(cacheKey, result);
return result;
```

### 5. **Transaction Management**
```java
try {
    transactionManager.begin();
    Object result = method.invoke(target, args);
    transactionManager.commit();
    return result;
} catch (Exception e) {
    transactionManager.rollback();
    throw e;
}
```

## 🔥 Why Use Dynamic Proxy?

### ✅ **Advantages:**
- **Non-intrusive** - No need to modify original code
- **Flexible** - Add behavior at runtime
- **Reusable** - Same proxy can be used for multiple objects
- **Clean separation** - Cross-cutting concerns handled separately
- **Dynamic** - Behavior can be changed at runtime

### ❌ **Disadvantages:**
- **Performance overhead** - Additional method calls
- **Debugging complexity** - Stack traces can be harder to follow
- **Interface requirement** - Only works with interfaces (use CGLIB for classes)
- **Runtime errors** - Some issues only surface at runtime

## 🛠️ Technologies Used

- **Java 17** - Core programming language
- **Spring Boot 3.2.0** - Framework for easy application setup
- **Gradle** - Build automation tool
- **Java Reflection API** - For dynamic method invocation
- **Proxy Pattern** - Design pattern implementation

## 🔄 How Dynamic Proxy Works

1. **Client calls method** on proxy object
2. **Proxy intercepts** the call and forwards to InvocationHandler
3. **InvocationHandler processes** the call (adds logging, security, etc.)
4. **Original method** is invoked on the real object
5. **Result is processed** and returned to client

```
Client.method() → Proxy.method() → InvocationHandler.invoke() → RealObject.method()
```

## 🚀 Future Enhancements

1. **AOP Integration** - Integrate with Spring AOP for more advanced features
2. **Custom Annotations** - Method-level configuration using annotations
3. **Metrics Collection** - Detailed performance and usage metrics
4. **Exception Handling** - Advanced error management and recovery
5. **Configuration Management** - External configuration for proxy behavior

## 🧪 Testing

The project demonstrates the proxy pattern with:
- **Method interception** - All calls are logged
- **Transparent delegation** - Original behavior is preserved
- **Runtime behavior modification** - No code changes needed

## 🤝 Contributing

If you have improvements or suggestions:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## 📚 Learn More

- [Java Dynamic Proxy Documentation](https://docs.oracle.com/javase/8/docs/technotes/guides/reflection/proxy.html)
- [Spring AOP Reference](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop)
- [Proxy Pattern Explained](https://refactoring.guru/design-patterns/proxy)

## 📝 License

This project is open source and available under the MIT License.

---

**Created with ❤️ for learning Java Dynamic Proxies**

> "Understanding the Proxy pattern is essential for modern Java development!"

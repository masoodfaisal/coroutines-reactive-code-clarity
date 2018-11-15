# How coroutines improve code readability

> Programs must be written for people to read, and only incidentally for machines to execute. — [Abelson and Sussman](https://en.wikiquote.org/wiki/Programming_languages)

[Kotlin](https://kotlinlang.org) coroutines provide an easy way to write highly scalable code, using traditional style of programming, while avoiding a thread allocated to each task. Much has been written about light weight threads and many resources are available on the internet, if you want to read more about this. 

In this blog, I focus on the code readability and how, in my opinion, coroutines provide a cleaner approach to write code as compared to reactive approach. I have used [Project Reactor](https://projectreactor.io) to showcase the reactive code, however, the example can be extended to any reactive library e.g. [RxJava](https://github.com/ReactiveX/RxJava), [CompleteableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html). Note that, coroutine based code scales as well as the code written using reactive approach. To me, coroutines is a win-win situation for developers.

You can read more about Kotlin coroutines [here](https://kotlinlang.org/docs/reference/coroutines-overview.html). A very exciting [Project Loom](https://openjdk.java.net/projects/loom/) is going to bring the light weight thread model to Java. It is a simialr concept as [GOLang routines](https://tour.golang.org/concurrency/1). 

## Approach

I have implemented the following workflow using both reactor and coroutines approach. The main function is **_processOrder_** which performs following: 
- The process starts with calling **_getOrderInfo_** and **_getShipmentInfo_**, in parallel. This part showcaes how calls can be made in a non-blocking way.
- When the methods calls menrtiond above are completed, the process calls the **_sendEmail_** method. 

Let's call the **_processOrder_** function as the **_process_** function and the individual **_getOrderInfo_**, **_getShipmentInfo_** and **_sendEmail_** functions as **_business_** functions.


This code is available in this [repo](https://github.com/masoodfaisal/coroutines-reactive-code-clarity). It is showcasing how Kotlin [couroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) can be used to write more readable code as compared to reactive approach without loosing the scalibility benefits.

## Simple is beautiful

In this section, a comparison of code is provided if it is written using Kotlin's aproach vs the reactive approach.

### **_Business Functions_**

Note that, in kotlin, the fuctions are just representing the business logic with very [less](https://medium.com/@elye.project/understanding-suspend-function-of-coroutines-de26b070c5ed) additional overhead.
```java
fun getOrderInfo(orderId: String): String {
    return "Order Info $orderId"
} 
```

The same function needs to understand the reactor concepts e.g. Mono as shown below. 
```java
Mono<String> getOrderInfo(String orderId) {
        return Mono.just("Order Info " + orderId);
    }
```

### **_Process Function_**
The main fucntion which calls the methods is seen below.
Note that using Kotlin's approach, the code is highly readable. The code documents itself for what business fucntion it is implementing.

```java
fun processOrder()  {
    val orderId = "SN19876"

    val orderInfo = async { getOrderInfo(orderId) }
    val shipmentInfo = async { getShipmentInfo(orderId) }

    sendEmail(shipmentInfo.await(), orderInfo.await())
}
```

The following code implements the same business process as implemented above. However, the readability of the code mentioned below is reduced and the non-fucntional behaviour of the code is being mixed with the fucntional aspect of the process.

```java


    void processOrder()  {
        String orderIdNumber = "SN19876";

        Mono.zip(getOrderInfo(orderIdNumber), getShipmentInfo(orderIdNumber))
                .flatMap(data -> sendEmail(data.getT1(), data.getT2()))
                .doOnSuccess(o -> System.out.println("Email sent " + o))
                .subscribe();


    }
```
From the example above, it is evident that coroutines provides a better alternative to write more readable code. The reactive code I have mentioned can be optimised to reduce more clutter, but there will always be reactive specific classes which I can avoid now.

[Kotlin](https://kotlinlang.org/docs/reference/) is an exciting new programming language specailly if you are coming from java background. You can start your kotlin journey by attending this [coursera course](https://www.coursera.org/learn/kotlin-for-java-developers).

This project uses the following technologies. 
-   [Project Reactor](https://projectreactor.io)
-   [Kotlin](https://kotlinlang.org)


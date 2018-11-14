# How coroutines can help improve code readability
> Programs must be written for people to read, and only incidentally for machines to execute. — [Abelson and Sussman](https://en.wikiquote.org/wiki/Programming_languages)

[Kotlin](https://kotlinlang.org) coroutines provides an easy way to write highly scalable code, using traditional style of programming, while avoiding a thread allocated to each task. Much is written about light weight threads and many resources are available on the internet, if you want to read more about this. 

In this blog, I am focusing on the code readability and how, in my opinion, coroutines provide a cleaner approach to write code as compared to reactive approach. I have used [Project Reactor](https://projectreactor.io) to showcase the reactive code, however, the example can be extended to any reactive library e.g. RxJava. Note that, coroutines based code scales as well as the code written using reactive approach. To me, coroutines is a win-win situation for developers.

You can read more about Kotlin coroutines [here](https://kotlinlang.org/docs/reference/coroutines-overview.html). A very exciting [Project Loom](https://openjdk.java.net/projects/loom/) is going to bring the light weight thread model to Java. It is a simialr concept as [GOLang routines](https://tour.golang.org/concurrency/1). 

## Approach
I have implemented the following workflow using both reactor and coroutines approach. The main function is **_processOrder_** which performs following: 
- The process starts with calling **_getOrderInfo_** and **_getShipmentInfo_**, in parallel. This part showcaes how calls can be made in a non-blocking way.
- When the methods calls, menrtiond above, are completed, the process calls the **_sendEmail_** method. 

Let's call the **_processOrder_** function as the **_process_** function and the individual **_getOrderInfo_**, **_getShipmentInfo_** and **_sendEmail_** functions as **_business_** fuctions.


This full set of code is available in this [repo](https://github.com/masoodfaisal/coroutines-reactive-code-clarity). It is showcasing how Kotlin [couroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) can be used to write more readable code as compared to reactive approach without loosing the scalibility benefits.

## Simple is beautiful

In this section, a comparison of code is provided if it is written using kotlin's aproach vs the reactive approach.

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
Note that using Kotlin's approach, the code is highly readable.

```java
fun processOrder()  {
    val orderId = "SN19876"

    val orderInfo = async { getOrderInfo(orderId) }
    val shipmentInfo = async { getShipmentInfo(orderId) }

    sendEmail(shipmentInfo.await(), orderInfo.await())
}
```

```java


    void processOrder()  {
        String orderIdNumber = "SN19876";

        Mono.zip(getOrderInfo(orderIdNumber), getShipmentInfo(orderIdNumber))
                .flatMap(data -> sendEmail(data.getT1(), data.getT2()))
                .doOnSuccess(o -> System.out.println("Got the result " + o))
                .subscribe();


    }
```
From the example above, it is evident that coroutines provides a bit better alternative to write more readable code, specailly in the case of concurrent calls. The reactive code I have mentioned can be optimised to reduce a bit more clutter, but there will always by reactive specific classes which I can avoid now.

This project is using following tools. 
-   [Project Reactor](https://projectreactor.io)
-   [kotlin](https://kotlinlang.org)


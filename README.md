# How coroutines can help improve code readability
Kotlin coroutines provides a way to use the imperative style of programming approach while avoiding a thread allocated to each task. Much is written about light weight threads and many resources are available on the internet, if you want to read more about this. 

In this blog, I am focusing on the code readibility and how, in my opinon, coroutines provide a cleaner approach to write code as compared to reactive approach. I have used [Project Reactor]() to showcase the reactive code, however, the example can be extended to any reactive library e.g. RxJava. Note that, coroutines based code scales as well as the code writtne using reactive approach. To me, this in a win-win situation.

You can read more about Kotlin coroutines [here](https://kotlinlang.org/docs/reference/coroutines-overview.html). A very exciting [Project Loom](https://openjdk.java.net/projects/loom/) is going to bring the light weight thread model to Java. It is a simialr concept as [GOLang routines](https://tour.golang.org/concurrency/1). 


This set of projects is showcasing how Kotlin [couroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) can be used to write more readable code as compared to reactive approach without loosing the scalibility benefits.

## Application flow
I have implemented the following workflow using both reactor and coroutines approach. The main requirement 

- The process starts with calling **_getOrderInfo_** and **_getShipmentInfo_**, in parallel. This part showcaes how calls can be made in a non-blocking way.
- When the methods calls, menrtiond above, are completed, the process calls the **_sendEmail_** method. 

## Observations


This project is using following tools 
-   [Netty](http://netty.io/), 
-   [Mono Reactive Streams](http://mongodb.github.io/mongo-java-driver-reactivestreams/1.9/) and 
-   [Project Reactor](https://projectreactor.io)
-   [Vegeta](https://github.com/tsenart/vegeta)


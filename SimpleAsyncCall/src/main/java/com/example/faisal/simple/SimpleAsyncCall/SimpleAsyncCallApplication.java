package com.example.faisal.simple.SimpleAsyncCall;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;


public class SimpleAsyncCallApplication {

    public static void main(String[] args) {

        SimpleAsyncCallApplication simpleAsyncCallApplication = new SimpleAsyncCallApplication();
        long initTime = System.currentTimeMillis();
        simpleAsyncCallApplication.processOrder();
        System.out.println("Time taken is " + (System.currentTimeMillis() - initTime));


    }


    void processOrder()  {
        String orderIdNumber = "SN19876";

        Mono<String> orderInfoMono = getOrderInfo(orderIdNumber);

        Mono<String> shipmentMono = getShipmentInfo(orderIdNumber);

        Mono.zip(orderInfoMono, shipmentMono)
        .flatMap(data -> sendEmail(data.getT1(), data.getT2()))
                .doOnSuccess(o -> System.out.println("Got the result " + o))
        .subscribe();

        //OR you can do this.

        Mono.zip(getOrderInfo(orderIdNumber), getShipmentInfo(orderIdNumber))
                .flatMap(data -> sendEmail(data.getT1(), data.getT2()))
                .doOnSuccess(o -> System.out.println("Got the result " + o))
                .subscribe();


    }

    /**
     * Get informtion about order
     */
    Mono<String> getOrderInfo(String orderId) {
        return Mono.just("Order Info " + orderId);
    }

    /**
     * Get information about shipment
     */
    Mono<String> getShipmentInfo(String orderId) {

        return Mono.just("Shipped for order "+ orderId);
    }

    Mono<Boolean> sendEmail(String shipmentInfo, String orderInfo) {
        System.out.println("Sending email for " + shipmentInfo + " with order " + orderInfo);
        return Mono.just(Boolean.TRUE);
    }
}

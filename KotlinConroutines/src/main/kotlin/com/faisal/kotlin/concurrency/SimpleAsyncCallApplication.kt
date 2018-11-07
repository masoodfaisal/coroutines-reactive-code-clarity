package com.faisal.kotlin.concurrency


import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {

    val timeTaken = measureTimeMillis { processOrder() }
    println("Time taken is $timeTaken")
}

fun processOrder() = runBlocking {
    val orderId = "SN19876"

    //make a call, but donot wait for it.
    val orderInfo = async { getOrderInfo(orderId) }

    //make a call, but donot wait for it.
    val shipmentInfo = async { getShipmentInfo(orderId) }

    //make the call, and wait for the results to arrive from the previous calls.
    sendEmail(shipmentInfo.await(), orderInfo.await())


}


/**
 * Get informtion about order
 */
fun getOrderInfo(orderId: String): String {
    return "Order Info $orderId"
}

/**
 * Get information about shipment
 */
fun getShipmentInfo(orderId: String): String {
    return "Shipped for order $orderId"
}

private fun sendEmail(shipmentInfo: String, orderInfo: String): Boolean {
    println("Sending email for $shipmentInfo with order $orderInfo")
    return true
}
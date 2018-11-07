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
    val orderInfo = async { getOrderInfo(orderId) }
    val shipmentInfo = async { getShipmentInfo(orderId) }

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
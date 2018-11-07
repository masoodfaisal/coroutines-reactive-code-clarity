package com.faisal.kotlin.concurrency

import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.Executors


fun main() = runBlocking {
    val serverSocket = ServerSocket(12321)

//    val serverSocketChannel = ServerSocketChannel.open()
//
//    serverSocketChannel.socket().bind(InetSocketAddress(9999))
//    serverSocketChannel.configureBlocking(false)
//
//    serverSocketChannel.accept();

    try {
        while (true) {
            println("Waiting for new connection")
            val clientSocket = serverSocket.accept()

            launch(Executors.newFixedThreadPool(20).asCoroutineDispatcher()) {
                handleConnection(clientSocket)
            }


        }
    } finally {
        serverSocket.close()
        println("Closing server socket")
    }
}


suspend fun handleConnection(clientSocket: Socket) {
    println("Connection accepted")
    val pw = PrintWriter(clientSocket.outputStream, true)
    pw.write("Soen stuff")
    val br = BufferedReader(InputStreamReader(clientSocket.inputStream))
    while (true) {
        val line = br.readLine() ?: break
        println("Received: $line")
        pw.write("$line\n")
        pw.flush()
        if (line == "exit") break
        yield()
    }
    br.close()
    pw.close()
    clientSocket.close()
    println("Closing connection, #${clientSocket.port} and ${clientSocket.localPort}")
}

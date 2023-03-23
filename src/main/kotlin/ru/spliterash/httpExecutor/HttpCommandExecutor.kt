package ru.spliterash.httpExecutor

import com.sun.net.httpserver.HttpServer
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.net.InetSocketAddress

class HttpCommandExecutor : JavaPlugin() {
    private var server: HttpServer? = null
    override fun onEnable() {
        saveDefaultConfig()
        val config = config
        val password = config.getString("security.password")

        val server = HttpServer.create(
            InetSocketAddress("0.0.0.0", config.getInt("server.port", 8080)),
            0
        )

        server.createContext("/executeCommand") { exchange ->
            if (exchange.requestMethod != "POST") {
                exchange.sendResponseHeaders(405, 0)
                exchange.close()
                return@createContext
            }
            val authHeader = exchange.requestHeaders.getFirst("authorization")

            if (authHeader != password) {
                exchange.sendResponseHeaders(401, 0)
                exchange.close()
                return@createContext
            }


            val command = exchange.requestBody.readAllBytes().decodeToString()

            exchange.responseHeaders.add("Content-Type", "application/text")
            val sender = CollectingCommandSender()
            val future = Bukkit.getScheduler().callSyncMethod(this@HttpCommandExecutor) {
                try {
                    Bukkit.dispatchCommand(sender, command)
                    ""
                } catch (ex: Exception) {
                    "Error execute command(${ex.javaClass.simpleName}): ${ex.message}\n"
                }
            }
            val start = future.get()
            val response = start + sender.response()
            val byteResponse = response.encodeToByteArray()
            exchange.sendResponseHeaders(200, byteResponse.size.toLong())

            exchange.responseBody.write(byteResponse)
            exchange.close()
        }

        server.start()
        this.server = server
    }

    override fun onDisable() {
        server?.stop(0)
    }
}
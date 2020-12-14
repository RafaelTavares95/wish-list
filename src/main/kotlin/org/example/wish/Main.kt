package org.example.wish

import io.javalin.Javalin
import org.example.wish.config.AppConfig

fun main(args: Array<String>) {
    val app = AppConfig().setup().start(7000)
    app.get("/") { ctx -> ctx.result("Wish List") }
}
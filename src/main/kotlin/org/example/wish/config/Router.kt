package org.example.wish.config

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import org.example.wish.controller.WishController
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class Router : KoinComponent {

    private val wishController: WishController by inject()

    fun register(app: Javalin){
        app.routes {
            path("wishes"){
                post(wishController::create)
                get(wishController::getWishes)
            }
        }

    }
}
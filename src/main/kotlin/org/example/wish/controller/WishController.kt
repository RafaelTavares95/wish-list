package org.example.wish.controller

import io.javalin.http.Context
import org.eclipse.jetty.http.HttpStatus
import org.example.wish.model.Wish
import org.example.wish.service.WishService
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class WishController : KoinComponent{
    private val wishService: WishService by inject()

    fun create(ctx: Context){
        ctx.bodyAsClass(Wish::class.java).let {
            ctx.status(HttpStatus.CREATED_201)
            wishService.save(it)
        }
    }

    fun update(ctx: Context){
        ctx.pathParam("wish-id").toLongOrNull().also { id ->
            ctx.bodyAsClass(Wish::class.java).let {
                ctx.status(HttpStatus.OK_200)
                if (id != null) {
                    wishService.update(id, it)
                }
            }
        }
    }

    fun getWishes(ctx: Context){
       ctx.json(wishService.listAll())
    }
}
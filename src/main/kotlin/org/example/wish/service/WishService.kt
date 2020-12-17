package org.example.wish.service

import org.example.wish.model.Wish
import org.example.wish.repository.WishRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

interface WishService {
    fun save(wish: Wish): Wish
    fun update(id:Long, wish: Wish): Wish
    fun listAll(): List<Wish>
}

class WishServiceImpl : KoinComponent, WishService{

    private val wishRepository: WishRepository by inject()

    override fun save(wish: Wish): Wish = this.wishRepository.save(wish)

    override fun update(id: Long, wish: Wish): Wish = this.wishRepository.update(id, wish)

    override fun listAll(): List<Wish> = this.wishRepository.listAll()
}
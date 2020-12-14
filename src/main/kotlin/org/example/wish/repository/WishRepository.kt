package org.example.wish.repository

import org.example.wish.model.Wish
import org.example.wish.model.enum.Status
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.standalone.KoinComponent

private object WishSchema : LongIdTable("wish"){
    //val id = long("id").autoIncrement().primaryKey()
    val name =  varchar("name", 255).nullable()
    val description = varchar("description", 255).nullable()
    val link = varchar("link", 255).nullable()
    val status = varchar("status", length = 255)
    val date = date("date").nullable()
}

fun ResultRow.toWish(): Wish = Wish(
    id = get(WishSchema.id).value,
    name = get(WishSchema.name),
    description = get(WishSchema.description),
    link = get(WishSchema.link),
    status = Status.valueOf(get(WishSchema.status)),
    date = get(WishSchema.date)
)

interface WishRepository{
    fun save(wish: Wish): Wish
    fun listAll(): List<Wish>
}

class WishRepositoryImpl: WishRepository, KoinComponent {

    init {
        transaction {
            SchemaUtils.create(WishSchema)
        }
    }

    override fun save(wish: Wish): Wish = transaction {
        val result = WishSchema.insert {
            it[name] = wish.name
            it[description] = wish.description
            it[link] = wish.link
            it[status] = wish.status.name
            it[date] = wish.date
        }
        wish.copy(id = result[WishSchema.id].value)
    }

    override fun listAll(): List<Wish> = transaction {
        WishSchema.selectAll().map {
            it.toWish()
        }.toList()
    }

}
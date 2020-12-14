package org.example.wish.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.example.wish.controller.WishController
import org.example.wish.repository.WishRepository
import org.example.wish.repository.WishRepositoryImpl
import org.example.wish.service.WishService
import org.example.wish.service.WishServiceImpl
import org.koin.dsl.module.module


object ModuleConfig {

    private val configModule = module {
        single { AppConfig() }
        single { Router() }
        single {
            HikariConfig().run {
                jdbcUrl = getProperty("jdbc.url")
                username = getProperty("db.username")
                password = (getProperty("db.password") as Any).toString()
                HikariDataSource(this)
            }
        }
    }

    private val wishModule = module {
        single { WishController() }
        single { WishServiceImpl() as WishService }
        single { WishRepositoryImpl() as WishRepository }
    }

    internal val allModules = listOf(
            configModule,
            wishModule
    )
}
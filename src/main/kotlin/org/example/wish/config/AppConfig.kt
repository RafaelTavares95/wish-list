package org.example.wish.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.zaxxer.hikari.HikariDataSource
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import org.jetbrains.exposed.sql.Database
import org.koin.core.KoinProperties
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import java.text.SimpleDateFormat


class AppConfig : KoinComponent {

    private val router: Router by inject()
    private val dataSource: HikariDataSource by inject()

    fun setup(): Javalin {
        StandAloneContext.startKoin(ModuleConfig.allModules,  KoinProperties(true, useKoinPropertiesFile = true))
        Database.connect(dataSource)
        return Javalin.create()
            .apply {
                router.register(this)
                configureMapper()
            }
    }

    private fun configureMapper() {
        JavalinJackson.configure(JacksonMapper())
    }

}

object JacksonMapper {
    private val mapper: ObjectMapper = jacksonObjectMapper()
        .registerModule(JodaModule())
        .registerModule(KotlinModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .setDateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))

    operator fun invoke(): ObjectMapper {
        return mapper
    }
}

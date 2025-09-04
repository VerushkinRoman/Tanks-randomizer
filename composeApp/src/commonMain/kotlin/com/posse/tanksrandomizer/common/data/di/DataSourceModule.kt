package com.posse.tanksrandomizer.common.data.di

import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSource
import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSourceImpl
import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val commonDataSourceModule = DI.Module("CommonDataSourceModule") {
    bind<Settings>() with singleton {
        Settings()
    }

    bind<HttpClient>() with singleton {
        HttpClient {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.BODY
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }

    bind<OfflineDataSource>(tag = DataSourceFor.Common) with singleton {
        OfflineDataSourceImpl(
            settings = instance(),
            dataSourceFor = DataSourceFor.Common
        )
    }
}

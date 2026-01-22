package com.posse.tanksrandomizer.common.data.di

import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSource
import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSourceImpl
import com.posse.tanksrandomizer.common.data.datasource.TokenManager
import com.posse.tanksrandomizer.common.data.datasource.TokenManagerImpl
import com.posse.tanksrandomizer.common.data.networking.NetworkChecker
import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.observable.makeObservable
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
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

expect fun getKtorLogger(): Logger

val commonLogger = object : Logger {
    override fun log(message: String) {
        println(message)
    }
}

val commonDataSourceModule = DI.Module("CommonDataSourceModule") {
    bind<Settings>() with singleton {
        Settings()
    }

    @OptIn(ExperimentalSettingsApi::class)
    bind<ObservableSettings>() with singleton {
        instance<Settings>().makeObservable()
    }

    bind<HttpClient>() with singleton {
        HttpClient {
            install(Logging) {
                logger = getKtorLogger()
                level = LogLevel.ALL
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
            observableSettings = instance(),
            dataSourceFor = DataSourceFor.Common
        )
    }

    bind<TokenManager>() with singleton {
        TokenManagerImpl(
            settings = instance(),
            httpClient = instance(),
            pagedScreenDataSource = instance(DataSourceFor.OnlineScreen),
            networkChecker = NetworkChecker(configuration = instance()),
            dispatchers = instance()
        )
    }
}

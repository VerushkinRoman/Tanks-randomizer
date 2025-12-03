package com.posse.tanksrandomizer.common.compose

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.navigationevent.NavigationEventDispatcher
import androidx.navigationevent.NavigationEventDispatcherOwner
import androidx.navigationevent.compose.LocalNavigationEventDispatcherOwner
import com.posse.tanksrandomizer.common.compose.components.AppBackground
import com.posse.tanksrandomizer.common.compose.theme.AppTheme
import com.posse.tanksrandomizer.common.compose.utils.LocalElementSize
import com.posse.tanksrandomizer.common.compose.utils.LocalSizeClass
import com.posse.tanksrandomizer.common.compose.utils.elementSize
import com.posse.tanksrandomizer.common.compose.utils.screenSize
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.AppLocale
import com.posse.tanksrandomizer.feature_settings_screen.domain.repository.SettingsRepository
import io.github.sudarshanmhasrup.localina.api.LocaleUpdater
import io.github.sudarshanmhasrup.localina.api.LocalinaApp
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI

@Composable
internal fun CommonPlatformApp(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) = withDI(Inject.di.di) {
    AppTheme {
        val repository: SettingsRepository by rememberInstance()

        val rootDispatcherOwner = remember {
            object : NavigationEventDispatcherOwner {
                override val navigationEventDispatcher = NavigationEventDispatcher()
            }
        }

        CompositionLocalProvider(
            LocalNavigationEventDispatcherOwner provides rootDispatcherOwner
        ) {
            LocalinaApp {
                val currentLanguageCode = Locale.current.language

                LaunchedEffect(true) {
                    val locale = repository.getLocale()

                    if (locale == null) {
                        val currentLocale = AppLocale
                            .entries
                            .find { it.name.lowercase() == currentLanguageCode }
                            ?: AppLocale.En

                        repository.setLocale(currentLocale)
                        LocaleUpdater.updateLocale(currentLocale.name.lowercase())
                    } else {
                        LocaleUpdater.updateLocale(locale.name.lowercase())
                    }
                }

                BoxWithConstraints(
                    contentAlignment = Alignment.Center,
                    modifier = modifier,
                ) {
                    CompositionLocalProvider(
                        LocalElementSize provides elementSize(maxWidth),
                        LocalSizeClass provides screenSize(maxWidth, maxHeight),
                    ) {
                        AppBackground(
                            modifier = Modifier.fillMaxSize(),
                            content = content,
                        )
                    }
                }
            }
        }
    }
}

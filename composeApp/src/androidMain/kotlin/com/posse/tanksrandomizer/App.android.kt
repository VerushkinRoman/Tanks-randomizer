package com.posse.tanksrandomizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.posse.tanksrandomizer.compose.App
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        var splashEnabled = true
        splashScreen.setKeepOnScreenCondition { splashEnabled }

        setContent {
            App(
                onDataLoaded = {
                    lifecycleScope.launch {
                        delay(300)
                        splashEnabled = false
                    }
                }
            )
        }
    }
}
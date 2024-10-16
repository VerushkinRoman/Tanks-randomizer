package com.posse.tanksrandomizer.compose.main_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.posse.tanksrandomizer.compose.main_screen.components.MainScreenContent
import com.posse.tanksrandomizer.data_source.DataSourceMultiplatformSettings
import com.posse.tanksrandomizer.presentation.MainViewModel
import com.posse.tanksrandomizer.repository.SettingsRepositoryImpl

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel {
        MainViewModel(SettingsRepositoryImpl(DataSourceMultiplatformSettings()))
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    MainScreenContent(
        viewState = state,
        onEvent = viewModel::obtainEvent,
        modifier = modifier
    )
}
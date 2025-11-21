package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.compose.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.CommonScreenColumn
import com.posse.tanksrandomizer.common.compose.base_components.SmallButtonWithTextAndImage
import com.posse.tanksrandomizer.common.compose.components.FiltersBlock
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models.OnlineScreenEvent
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models.OnlineScreenState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.lesta_logo
import tanks_randomizer.composeapp.generated.resources.logout
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun OnlineScreenContent(
    viewState: OnlineScreenState,
    runningAsOverlay: Boolean,
    onEvent: (OnlineScreenEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    CommonScreenColumn(
        runningAsOverlay = runningAsOverlay,
        modifier = modifier,
//        modifier = modifier
//            .padding(4.dp)
//            .border(
//                width = BorderWidth,
//                color = MaterialTheme.colorScheme.primary,
//                shape = CutCornerShape(topStartPercent = 0, topEndPercent = 2, bottomStartPercent = 2, bottomEndPercent = 0)
//            )
//            .padding(4.dp),
    ) {
        AccountAndGeneratedTankInfo(
            generatedTank = viewState.generatedTank,
            lastAccountUpdated = viewState.lastAccountUpdated,
            tanksOverall = viewState.tanksInGarage.size,
            tanksByFilter = viewState.tanksByFilter.size,
            loading = viewState.loading,
            onRefresh = { onEvent(OnlineScreenEvent.RefreshAccountPressed) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            Modifier.height(24.dp)
        )

        FiltersBlock(
            components = viewState.onlineFilters.components,
            onFilterItemClick = { onEvent(OnlineScreenEvent.FilterItemChanged(it)) },
            onDiceClick = { onEvent(OnlineScreenEvent.GenerateTankPressed) },
            onTrashClick = { onEvent(OnlineScreenEvent.TrashFilterPressed) },
            onSelectAllClick = { onEvent(OnlineScreenEvent.CheckAllPressed) },
            diceEnabled = viewState.tanksByFilter.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(24.dp))

        LogOutButton(
            onClick = { onEvent(OnlineScreenEvent.LogOutPressed) },
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
private fun LogOutButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SmallButtonWithTextAndImage(
        text = stringResource(Res.string.logout),
        painter = painterResource(Res.drawable.lesta_logo),
        textFirst = false,
        onClick = onClick,
        modifier = modifier
    )
}

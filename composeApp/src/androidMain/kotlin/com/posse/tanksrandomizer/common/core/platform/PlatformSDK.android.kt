package com.posse.tanksrandomizer.common.core.platform

import com.posse.tanksrandomizer.common.presentation.ads.di.rewardAdViewModelModule
import org.kodein.di.DI

internal actual val platformModule: DI.Module = DI.Module("AndroidModule") {
    importAll(
        rewardAdViewModelModule,
    )
}

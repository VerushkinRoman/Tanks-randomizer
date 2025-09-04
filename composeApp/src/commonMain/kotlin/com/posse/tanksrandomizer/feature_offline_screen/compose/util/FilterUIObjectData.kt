package com.posse.tanksrandomizer.feature_offline_screen.compose.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Tier
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Experience
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Pinned
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Status
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.TankType
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.check
import tanks_randomizer.composeapp.generated.resources.exp_bday
import tanks_randomizer.composeapp.generated.resources.exp_birthday
import tanks_randomizer.composeapp.generated.resources.exp_x2
import tanks_randomizer.composeapp.generated.resources.exp_x5
import tanks_randomizer.composeapp.generated.resources.tier1
import tanks_randomizer.composeapp.generated.resources.tier10
import tanks_randomizer.composeapp.generated.resources.tier2
import tanks_randomizer.composeapp.generated.resources.tier3
import tanks_randomizer.composeapp.generated.resources.tier4
import tanks_randomizer.composeapp.generated.resources.tier5
import tanks_randomizer.composeapp.generated.resources.tier6
import tanks_randomizer.composeapp.generated.resources.tier7
import tanks_randomizer.composeapp.generated.resources.tier8
import tanks_randomizer.composeapp.generated.resources.tier9
import tanks_randomizer.composeapp.generated.resources.tier_1
import tanks_randomizer.composeapp.generated.resources.tier_10
import tanks_randomizer.composeapp.generated.resources.tier_2
import tanks_randomizer.composeapp.generated.resources.tier_3
import tanks_randomizer.composeapp.generated.resources.tier_4
import tanks_randomizer.composeapp.generated.resources.tier_5
import tanks_randomizer.composeapp.generated.resources.tier_6
import tanks_randomizer.composeapp.generated.resources.tier_7
import tanks_randomizer.composeapp.generated.resources.tier_8
import tanks_randomizer.composeapp.generated.resources.tier_9
import tanks_randomizer.composeapp.generated.resources.nation_china
import tanks_randomizer.composeapp.generated.resources.nation_europe
import tanks_randomizer.composeapp.generated.resources.nation_france
import tanks_randomizer.composeapp.generated.resources.nation_germany
import tanks_randomizer.composeapp.generated.resources.nation_great_britain
import tanks_randomizer.composeapp.generated.resources.nation_japan
import tanks_randomizer.composeapp.generated.resources.nation_unknown
import tanks_randomizer.composeapp.generated.resources.nation_usa
import tanks_randomizer.composeapp.generated.resources.nation_ussr
import tanks_randomizer.composeapp.generated.resources.pinned
import tanks_randomizer.composeapp.generated.resources.status_elite
import tanks_randomizer.composeapp.generated.resources.status_not_elite
import tanks_randomizer.composeapp.generated.resources.tank_type_collection
import tanks_randomizer.composeapp.generated.resources.tank_type_common
import tanks_randomizer.composeapp.generated.resources.tank_type_premium
import tanks_randomizer.composeapp.generated.resources.trash
import tanks_randomizer.composeapp.generated.resources.type_heavy
import tanks_randomizer.composeapp.generated.resources.type_light
import tanks_randomizer.composeapp.generated.resources.type_medium
import tanks_randomizer.composeapp.generated.resources.type_td

enum class FilterUIObjectData(
    val drawableResource: DrawableResource,
    val stringResource: StringResource,
) {
    Tier1(
        drawableResource = Res.drawable.tier_1,
        stringResource = Res.string.tier1,
    ),
    Tier2(
        drawableResource = Res.drawable.tier_2,
        stringResource = Res.string.tier2,
    ),
    Tier3(
        drawableResource = Res.drawable.tier_3,
        stringResource = Res.string.tier3,
    ),
    Tier4(
        drawableResource = Res.drawable.tier_4,
        stringResource = Res.string.tier4,
    ),
    Tier5(
        drawableResource = Res.drawable.tier_5,
        stringResource = Res.string.tier5,
    ),
    Tier6(
        drawableResource = Res.drawable.tier_6,
        stringResource = Res.string.tier6,
    ),
    Tier7(
        drawableResource = Res.drawable.tier_7,
        stringResource = Res.string.tier7,
    ),
    Tier8(
        drawableResource = Res.drawable.tier_8,
        stringResource = Res.string.tier8,
    ),
    Tier9(
        drawableResource = Res.drawable.tier_9,
        stringResource = Res.string.tier9,
    ),
    Tier10(
        drawableResource = Res.drawable.tier_10,
        stringResource = Res.string.tier10,
    ),

    X2(
        drawableResource = Res.drawable.exp_x2,
        stringResource = Res.string.exp_x2,
    ),
    BDay(
        drawableResource = Res.drawable.exp_birthday,
        stringResource = Res.string.exp_bday,
    ),
    X5(
        drawableResource = Res.drawable.exp_x5,
        stringResource = Res.string.exp_x5,
    ),

    China(
        drawableResource = Res.drawable.nation_china,
        stringResource = Res.string.nation_china,
    ),
    Europe(
        drawableResource = Res.drawable.nation_europe,
        stringResource = Res.string.nation_europe,
    ),
    France(
        drawableResource = Res.drawable.nation_france,
        stringResource = Res.string.nation_france,
    ),
    Germany(
        drawableResource = Res.drawable.nation_germany,
        stringResource = Res.string.nation_germany,
    ),
    GreatBritain(
        drawableResource = Res.drawable.nation_great_britain,
        stringResource = Res.string.nation_great_britain,
    ),
    Japan(
        drawableResource = Res.drawable.nation_japan,
        stringResource = Res.string.nation_japan,
    ),
    Unknown(
        drawableResource = Res.drawable.nation_unknown,
        stringResource = Res.string.nation_unknown,
    ),
    USA(
        drawableResource = Res.drawable.nation_usa,
        stringResource = Res.string.nation_usa,
    ),
    USSR(
        drawableResource = Res.drawable.nation_ussr,
        stringResource = Res.string.nation_ussr,
    ),

    Pinned(
        drawableResource = Res.drawable.pinned,
        stringResource = Res.string.pinned,
    ),

    Elite(
        drawableResource = Res.drawable.status_elite,
        stringResource = Res.string.status_elite,
    ),
    NotElite(
        drawableResource = Res.drawable.status_not_elite,
        stringResource = Res.string.status_not_elite,
    ),

    Collection(
        drawableResource = Res.drawable.tank_type_collection,
        stringResource = Res.string.tank_type_collection,
    ),
    Common(
        drawableResource = Res.drawable.tank_type_common,
        stringResource = Res.string.tank_type_common,
    ),
    Premium(
        drawableResource = Res.drawable.tank_type_premium,
        stringResource = Res.string.tank_type_premium,
    ),

    Heavy(
        drawableResource = Res.drawable.type_heavy,
        stringResource = Res.string.type_heavy,
    ),
    Light(
        drawableResource = Res.drawable.type_light,
        stringResource = Res.string.type_light,
    ),
    Medium(
        drawableResource = Res.drawable.type_medium,
        stringResource = Res.string.type_medium,
    ),
    TankDestroyer(
        drawableResource = Res.drawable.type_td,
        stringResource = Res.string.type_td,
    ),
}

enum class FilterUIObjectSwitchData(
    val enabledDrawableResource: ImageVector,
    val disabledDrawableResource: ImageVector,
    val enabledStringResource: StringResource,
    val disabledStringResource: StringResource,
) {
    Switch(
        enabledDrawableResource = Icons.Rounded.Close,
        disabledDrawableResource = Icons.Rounded.Check,
        enabledStringResource = Res.string.trash,
        disabledStringResource = Res.string.check,
    )
}

@Composable
fun <T> getFilterImage(item: ItemStatus<T>): Painter {
    return when (item) {
        is Tier -> {
            when (item) {
                is Tier.Tier1 -> painterResource(FilterUIObjectData.Tier1.drawableResource)
                is Tier.Tier2 -> painterResource(FilterUIObjectData.Tier2.drawableResource)
                is Tier.Tier3 -> painterResource(FilterUIObjectData.Tier3.drawableResource)
                is Tier.Tier4 -> painterResource(FilterUIObjectData.Tier4.drawableResource)
                is Tier.Tier5 -> painterResource(FilterUIObjectData.Tier5.drawableResource)
                is Tier.Tier6 -> painterResource(FilterUIObjectData.Tier6.drawableResource)
                is Tier.Tier7 -> painterResource(FilterUIObjectData.Tier7.drawableResource)
                is Tier.Tier8 -> painterResource(FilterUIObjectData.Tier8.drawableResource)
                is Tier.Tier9 -> painterResource(FilterUIObjectData.Tier9.drawableResource)
                is Tier.Tier10 -> painterResource(FilterUIObjectData.Tier10.drawableResource)
                is Tier.TierSwitch -> rememberVectorPainter(
                    if (item.selected) FilterUIObjectSwitchData.Switch.enabledDrawableResource
                    else FilterUIObjectSwitchData.Switch.disabledDrawableResource
                )
            }
        }

        is Experience -> {
            when (item) {
                is Experience.BDay -> painterResource(FilterUIObjectData.BDay.drawableResource)
                is Experience.X2 -> painterResource(FilterUIObjectData.X2.drawableResource)
                is Experience.X5 -> painterResource(FilterUIObjectData.X5.drawableResource)
                is Experience.ExperienceSwitch -> rememberVectorPainter(
                    if (item.selected) FilterUIObjectSwitchData.Switch.enabledDrawableResource
                    else FilterUIObjectSwitchData.Switch.disabledDrawableResource
                )
            }
        }

        is Nation -> {
            when (item) {
                is Nation.China -> painterResource(FilterUIObjectData.China.drawableResource)
                is Nation.European -> painterResource(FilterUIObjectData.Europe.drawableResource)
                is Nation.France -> painterResource(FilterUIObjectData.France.drawableResource)
                is Nation.Germany -> painterResource(FilterUIObjectData.Germany.drawableResource)
                is Nation.UK -> painterResource(FilterUIObjectData.GreatBritain.drawableResource)
                is Nation.Japan -> painterResource(FilterUIObjectData.Japan.drawableResource)
                is Nation.USA -> painterResource(FilterUIObjectData.USA.drawableResource)
                is Nation.USSR -> painterResource(FilterUIObjectData.USSR.drawableResource)
                is Nation.Other -> painterResource(FilterUIObjectData.Unknown.drawableResource)
                is Nation.NationSwitch -> rememberVectorPainter(
                    if (item.selected) FilterUIObjectSwitchData.Switch.enabledDrawableResource
                    else FilterUIObjectSwitchData.Switch.disabledDrawableResource
                )
            }
        }

        is Pinned -> {
            when (item) {
                is Pinned.Status -> painterResource(FilterUIObjectData.Pinned.drawableResource)
                is Pinned.PinnedSwitch -> rememberVectorPainter(
                    if (item.selected) FilterUIObjectSwitchData.Switch.enabledDrawableResource
                    else FilterUIObjectSwitchData.Switch.disabledDrawableResource
                )
            }
        }

        is Status -> {
            when (item) {
                is Status.Elite -> painterResource(FilterUIObjectData.Elite.drawableResource)
                is Status.NotElite -> painterResource(FilterUIObjectData.NotElite.drawableResource)
                is Status.StatusSwitch -> rememberVectorPainter(
                    if (item.selected) FilterUIObjectSwitchData.Switch.enabledDrawableResource
                    else FilterUIObjectSwitchData.Switch.disabledDrawableResource
                )
            }
        }

        is TankType -> {
            when (item) {
                is TankType.Collection -> painterResource(FilterUIObjectData.Collection.drawableResource)
                is TankType.Common -> painterResource(FilterUIObjectData.Common.drawableResource)
                is TankType.Premium -> painterResource(FilterUIObjectData.Premium.drawableResource)
                is TankType.TankTypeSwitch -> rememberVectorPainter(
                    if (item.selected) FilterUIObjectSwitchData.Switch.enabledDrawableResource
                    else FilterUIObjectSwitchData.Switch.disabledDrawableResource
                )
            }
        }

        is Type -> {
            when (item) {
                is Type.Heavy -> painterResource(FilterUIObjectData.Heavy.drawableResource)
                is Type.Light -> painterResource(FilterUIObjectData.Light.drawableResource)
                is Type.Medium -> painterResource(FilterUIObjectData.Medium.drawableResource)
                is Type.TankDestroyer -> painterResource(FilterUIObjectData.TankDestroyer.drawableResource)
                is Type.TypeSwitch -> rememberVectorPainter(
                    if (item.selected) FilterUIObjectSwitchData.Switch.enabledDrawableResource
                    else FilterUIObjectSwitchData.Switch.disabledDrawableResource
                )
            }
        }

        else -> throw RuntimeException("wrong item: $item")
    }
}


@Composable
fun <T> getFilterName(item: ItemStatus<T>): String {
    return when (item) {
        is Tier -> {
            when (item) {
                is Tier.Tier1 -> stringResource(FilterUIObjectData.Tier1.stringResource)
                is Tier.Tier2 -> stringResource(FilterUIObjectData.Tier2.stringResource)
                is Tier.Tier3 -> stringResource(FilterUIObjectData.Tier3.stringResource)
                is Tier.Tier4 -> stringResource(FilterUIObjectData.Tier4.stringResource)
                is Tier.Tier5 -> stringResource(FilterUIObjectData.Tier5.stringResource)
                is Tier.Tier6 -> stringResource(FilterUIObjectData.Tier6.stringResource)
                is Tier.Tier7 -> stringResource(FilterUIObjectData.Tier7.stringResource)
                is Tier.Tier8 -> stringResource(FilterUIObjectData.Tier8.stringResource)
                is Tier.Tier9 -> stringResource(FilterUIObjectData.Tier9.stringResource)
                is Tier.Tier10 -> stringResource(FilterUIObjectData.Tier10.stringResource)
                is Tier.TierSwitch -> stringResource(
                    if (item.selected) FilterUIObjectSwitchData.Switch.enabledStringResource
                    else FilterUIObjectSwitchData.Switch.disabledStringResource
                )
            }
        }

        is Experience -> {
            when (item) {
                is Experience.BDay -> stringResource(FilterUIObjectData.BDay.stringResource)
                is Experience.X2 -> stringResource(FilterUIObjectData.X2.stringResource)
                is Experience.X5 -> stringResource(FilterUIObjectData.X5.stringResource)
                is Experience.ExperienceSwitch -> stringResource(
                    if (item.selected) FilterUIObjectSwitchData.Switch.enabledStringResource
                    else FilterUIObjectSwitchData.Switch.disabledStringResource
                )
            }
        }

        is Nation -> {
            when (item) {
                is Nation.China -> stringResource(FilterUIObjectData.China.stringResource)
                is Nation.European -> stringResource(FilterUIObjectData.Europe.stringResource)
                is Nation.France -> stringResource(FilterUIObjectData.France.stringResource)
                is Nation.Germany -> stringResource(FilterUIObjectData.Germany.stringResource)
                is Nation.UK -> stringResource(FilterUIObjectData.GreatBritain.stringResource)
                is Nation.Japan -> stringResource(FilterUIObjectData.Japan.stringResource)
                is Nation.USA -> stringResource(FilterUIObjectData.USA.stringResource)
                is Nation.USSR -> stringResource(FilterUIObjectData.USSR.stringResource)
                is Nation.Other -> stringResource(FilterUIObjectData.Unknown.stringResource)
                is Nation.NationSwitch -> stringResource(
                    if (item.selected) FilterUIObjectSwitchData.Switch.enabledStringResource
                    else FilterUIObjectSwitchData.Switch.disabledStringResource
                )
            }
        }

        is Pinned -> {
            when (item) {
                is Pinned.Status -> stringResource(FilterUIObjectData.Pinned.stringResource)
                is Pinned.PinnedSwitch -> stringResource(
                    if (item.selected) FilterUIObjectSwitchData.Switch.enabledStringResource
                    else FilterUIObjectSwitchData.Switch.disabledStringResource
                )
            }
        }

        is Status -> {
            when (item) {
                is Status.Elite -> stringResource(FilterUIObjectData.Elite.stringResource)
                is Status.NotElite -> stringResource(FilterUIObjectData.NotElite.stringResource)
                is Status.StatusSwitch -> stringResource(
                    if (item.selected) FilterUIObjectSwitchData.Switch.enabledStringResource
                    else FilterUIObjectSwitchData.Switch.disabledStringResource
                )
            }
        }

        is TankType -> {
            when (item) {
                is TankType.Collection -> stringResource(FilterUIObjectData.Collection.stringResource)
                is TankType.Common -> stringResource(FilterUIObjectData.Common.stringResource)
                is TankType.Premium -> stringResource(FilterUIObjectData.Premium.stringResource)
                is TankType.TankTypeSwitch -> stringResource(
                    if (item.selected) FilterUIObjectSwitchData.Switch.enabledStringResource
                    else FilterUIObjectSwitchData.Switch.disabledStringResource
                )
            }
        }

        is Type -> {
            when (item) {
                is Type.Heavy -> stringResource(FilterUIObjectData.Heavy.stringResource)
                is Type.Light -> stringResource(FilterUIObjectData.Light.stringResource)
                is Type.Medium -> stringResource(FilterUIObjectData.Medium.stringResource)
                is Type.TankDestroyer -> stringResource(FilterUIObjectData.TankDestroyer.stringResource)
                is Type.TypeSwitch -> stringResource(
                    if (item.selected) FilterUIObjectSwitchData.Switch.enabledStringResource
                    else FilterUIObjectSwitchData.Switch.disabledStringResource
                )
            }
        }

        else -> throw RuntimeException("wrong item: $item")
    }
}
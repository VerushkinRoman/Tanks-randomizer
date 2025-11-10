package com.posse.tanksrandomizer.common.compose.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.theme.tankTypesColors
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Tier
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Experience
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.MarkCount
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Pinned
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Status
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.TankType
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.OnlineFilterObjects.Mastery
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.OnlineFilterObjects.Premium
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.class1
import tanks_randomizer.composeapp.generated.resources.class2
import tanks_randomizer.composeapp.generated.resources.class3
import tanks_randomizer.composeapp.generated.resources.class_1
import tanks_randomizer.composeapp.generated.resources.class_2
import tanks_randomizer.composeapp.generated.resources.class_3
import tanks_randomizer.composeapp.generated.resources.class_master
import tanks_randomizer.composeapp.generated.resources.class_none
import tanks_randomizer.composeapp.generated.resources.exp_bday
import tanks_randomizer.composeapp.generated.resources.exp_birthday
import tanks_randomizer.composeapp.generated.resources.exp_x2
import tanks_randomizer.composeapp.generated.resources.exp_x5
import tanks_randomizer.composeapp.generated.resources.mark1
import tanks_randomizer.composeapp.generated.resources.mark2
import tanks_randomizer.composeapp.generated.resources.mark3
import tanks_randomizer.composeapp.generated.resources.nation_china
import tanks_randomizer.composeapp.generated.resources.nation_european
import tanks_randomizer.composeapp.generated.resources.nation_france
import tanks_randomizer.composeapp.generated.resources.nation_germany
import tanks_randomizer.composeapp.generated.resources.nation_japan
import tanks_randomizer.composeapp.generated.resources.nation_other
import tanks_randomizer.composeapp.generated.resources.nation_uk
import tanks_randomizer.composeapp.generated.resources.nation_usa
import tanks_randomizer.composeapp.generated.resources.nation_ussr
import tanks_randomizer.composeapp.generated.resources.no_marks
import tanks_randomizer.composeapp.generated.resources.pinned
import tanks_randomizer.composeapp.generated.resources.status_elite
import tanks_randomizer.composeapp.generated.resources.status_not_elite
import tanks_randomizer.composeapp.generated.resources.tank_type
import tanks_randomizer.composeapp.generated.resources.tank_type_collection
import tanks_randomizer.composeapp.generated.resources.tank_type_common
import tanks_randomizer.composeapp.generated.resources.tank_type_premium
import tanks_randomizer.composeapp.generated.resources.tank_type_premiumized
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
    European(
        drawableResource = Res.drawable.nation_european,
        stringResource = Res.string.nation_european,
    ),
    France(
        drawableResource = Res.drawable.nation_france,
        stringResource = Res.string.nation_france,
    ),
    Germany(
        drawableResource = Res.drawable.nation_germany,
        stringResource = Res.string.nation_germany,
    ),
    UK(
        drawableResource = Res.drawable.nation_uk,
        stringResource = Res.string.nation_uk,
    ),
    Japan(
        drawableResource = Res.drawable.nation_japan,
        stringResource = Res.string.nation_japan,
    ),
    Other(
        drawableResource = Res.drawable.nation_other,
        stringResource = Res.string.nation_other,
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
        drawableResource = Res.drawable.tank_type,
        stringResource = Res.string.tank_type_collection,
    ),
    Common(
        drawableResource = Res.drawable.tank_type,
        stringResource = Res.string.tank_type_common,
    ),
    Premiumized(
        drawableResource = Res.drawable.tank_type,
        stringResource = Res.string.tank_type_premiumized,
    ),
    Premium(
        drawableResource = Res.drawable.tank_type,
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

    NoMarks(
        drawableResource = Res.drawable.no_marks,
        stringResource = Res.string.no_marks,
    ),
    Mark1(
        drawableResource = Res.drawable.mark1,
        stringResource = Res.string.mark1,
    ),
    Mark2(
        drawableResource = Res.drawable.mark2,
        stringResource = Res.string.mark2,
    ),
    Mark3(
        drawableResource = Res.drawable.mark3,
        stringResource = Res.string.mark3,
    ),

    MasteryNone(
        drawableResource = Res.drawable.class_none,
        stringResource = Res.string.class_none,
    ),
    MasteryClass3(
        drawableResource = Res.drawable.class_3,
        stringResource = Res.string.class3,
    ),
    MasteryClass2(
        drawableResource = Res.drawable.class_2,
        stringResource = Res.string.class2,
    ),
    MasteryClass1(
        drawableResource = Res.drawable.class_1,
        stringResource = Res.string.class1,
    ),
    MasteryMaster(
        drawableResource = Res.drawable.class_master,
        stringResource = Res.string.class_master,
    ),
}

@Composable
fun getFilterImage(item: ItemStatus<*>): Painter {
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
            }
        }

        is Experience -> {
            when (item) {
                is Experience.BDay -> painterResource(FilterUIObjectData.BDay.drawableResource)
                is Experience.X2 -> painterResource(FilterUIObjectData.X2.drawableResource)
                is Experience.X5 -> painterResource(FilterUIObjectData.X5.drawableResource)
            }
        }

        is Nation -> {
            when (item) {
                is Nation.China -> painterResource(FilterUIObjectData.China.drawableResource)
                is Nation.European -> painterResource(FilterUIObjectData.European.drawableResource)
                is Nation.France -> painterResource(FilterUIObjectData.France.drawableResource)
                is Nation.Germany -> painterResource(FilterUIObjectData.Germany.drawableResource)
                is Nation.UK -> painterResource(FilterUIObjectData.UK.drawableResource)
                is Nation.Japan -> painterResource(FilterUIObjectData.Japan.drawableResource)
                is Nation.USA -> painterResource(FilterUIObjectData.USA.drawableResource)
                is Nation.USSR -> painterResource(FilterUIObjectData.USSR.drawableResource)
                is Nation.Other -> painterResource(FilterUIObjectData.Other.drawableResource)
            }
        }

        is Pinned -> {
            when (item) {
                is Pinned.Status -> painterResource(FilterUIObjectData.Pinned.drawableResource)
            }
        }

        is Status -> {
            when (item) {
                is Status.Elite -> painterResource(FilterUIObjectData.Elite.drawableResource)
                is Status.NotElite -> painterResource(FilterUIObjectData.NotElite.drawableResource)
            }
        }

        is TankType -> {
            when (item) {
                is TankType.Collection -> painterResource(FilterUIObjectData.Collection.drawableResource)
                is TankType.Common -> painterResource(FilterUIObjectData.Common.drawableResource)
                is TankType.Premiumized -> painterResource(FilterUIObjectData.Premiumized.drawableResource)
                is TankType.Premium -> painterResource(FilterUIObjectData.Premium.drawableResource)
            }
        }

        is Type -> {
            when (item) {
                is Type.Heavy -> painterResource(FilterUIObjectData.Heavy.drawableResource)
                is Type.Light -> painterResource(FilterUIObjectData.Light.drawableResource)
                is Type.Medium -> painterResource(FilterUIObjectData.Medium.drawableResource)
                is Type.TankDestroyer -> painterResource(FilterUIObjectData.TankDestroyer.drawableResource)
            }
        }

        is MarkCount -> {
            when (item) {
                is MarkCount.NoMarks -> painterResource(FilterUIObjectData.NoMarks.drawableResource)
                is MarkCount.Mark1 -> painterResource(FilterUIObjectData.Mark1.drawableResource)
                is MarkCount.Mark2 -> painterResource(FilterUIObjectData.Mark2.drawableResource)
                is MarkCount.Mark3 -> painterResource(FilterUIObjectData.Mark3.drawableResource)
            }
        }

        is Mastery -> {
            when (item) {
                is Mastery.None -> painterResource(FilterUIObjectData.MasteryNone.drawableResource)
                is Mastery.Class3 -> painterResource(FilterUIObjectData.MasteryClass3.drawableResource)
                is Mastery.Class2 -> painterResource(FilterUIObjectData.MasteryClass2.drawableResource)
                is Mastery.Class1 -> painterResource(FilterUIObjectData.MasteryClass1.drawableResource)
                is Mastery.Master -> painterResource(FilterUIObjectData.MasteryMaster.drawableResource)
            }
        }

        is Premium -> {
            when (item) {
                is Premium.Common -> painterResource(FilterUIObjectData.Common.drawableResource)
                is Premium.IsPremium -> painterResource(FilterUIObjectData.Premium.drawableResource)
            }
        }

        else -> throw RuntimeException("wrong item: $item")
    }
}

@Composable
fun getFilterName(item: ItemStatus<*>): String {
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
            }
        }

        is Experience -> {
            when (item) {
                is Experience.BDay -> stringResource(FilterUIObjectData.BDay.stringResource)
                is Experience.X2 -> stringResource(FilterUIObjectData.X2.stringResource)
                is Experience.X5 -> stringResource(FilterUIObjectData.X5.stringResource)
            }
        }

        is Nation -> {
            when (item) {
                is Nation.China -> stringResource(FilterUIObjectData.China.stringResource)
                is Nation.European -> stringResource(FilterUIObjectData.European.stringResource)
                is Nation.France -> stringResource(FilterUIObjectData.France.stringResource)
                is Nation.Germany -> stringResource(FilterUIObjectData.Germany.stringResource)
                is Nation.UK -> stringResource(FilterUIObjectData.UK.stringResource)
                is Nation.Japan -> stringResource(FilterUIObjectData.Japan.stringResource)
                is Nation.USA -> stringResource(FilterUIObjectData.USA.stringResource)
                is Nation.USSR -> stringResource(FilterUIObjectData.USSR.stringResource)
                is Nation.Other -> stringResource(FilterUIObjectData.Other.stringResource)
            }
        }

        is Pinned -> {
            when (item) {
                is Pinned.Status -> stringResource(FilterUIObjectData.Pinned.stringResource)
            }
        }

        is Status -> {
            when (item) {
                is Status.Elite -> stringResource(FilterUIObjectData.Elite.stringResource)
                is Status.NotElite -> stringResource(FilterUIObjectData.NotElite.stringResource)
            }
        }

        is TankType -> {
            when (item) {
                is TankType.Collection -> stringResource(FilterUIObjectData.Collection.stringResource)
                is TankType.Common -> stringResource(FilterUIObjectData.Common.stringResource)
                is TankType.Premiumized -> stringResource(FilterUIObjectData.Premiumized.stringResource)
                is TankType.Premium -> stringResource(FilterUIObjectData.Premium.stringResource)
            }
        }

        is Type -> {
            when (item) {
                is Type.Heavy -> stringResource(FilterUIObjectData.Heavy.stringResource)
                is Type.Light -> stringResource(FilterUIObjectData.Light.stringResource)
                is Type.Medium -> stringResource(FilterUIObjectData.Medium.stringResource)
                is Type.TankDestroyer -> stringResource(FilterUIObjectData.TankDestroyer.stringResource)
            }
        }

        is MarkCount -> {
            when (item) {
                is MarkCount.NoMarks -> stringResource(FilterUIObjectData.NoMarks.stringResource)
                is MarkCount.Mark1 -> stringResource(FilterUIObjectData.Mark1.stringResource)
                is MarkCount.Mark2 -> stringResource(FilterUIObjectData.Mark2.stringResource)
                is MarkCount.Mark3 -> stringResource(FilterUIObjectData.Mark3.stringResource)
            }
        }

        is Mastery -> {
            when (item) {
                is Mastery.None -> stringResource(FilterUIObjectData.MasteryNone.stringResource)
                is Mastery.Class3 -> stringResource(FilterUIObjectData.MasteryClass3.stringResource)
                is Mastery.Class2 -> stringResource(FilterUIObjectData.MasteryClass2.stringResource)
                is Mastery.Class1 -> stringResource(FilterUIObjectData.MasteryClass1.stringResource)
                is Mastery.Master -> stringResource(FilterUIObjectData.MasteryMaster.stringResource)
            }
        }

        is Premium -> {
            when (item) {
                is Premium.Common -> stringResource(FilterUIObjectData.Common.stringResource)
                is Premium.IsPremium -> stringResource(FilterUIObjectData.Premium.stringResource)
            }
        }

        else -> throw RuntimeException("wrong item: $item")
    }
}

@Composable
fun ItemStatus<*>.useFullSize(): Boolean {
    return when (this) {
        is Nation, is Experience, is Mastery -> true
        else -> false
    }
}

@Composable
fun ItemStatus<*>.useSquare(): Boolean {
    return when (this) {
        is Nation -> false
        else -> true
    }
}

@Composable
fun ItemStatus<*>.additionalPadding(): Dp {
    return when (this) {
        is Nation -> 0.dp
        is Mastery -> 6.dp
        else -> 3.dp
    }
}

@Composable
fun ItemStatus<*>.color(selected: Boolean): Color {
    return when (this) {
        is Nation, is Experience, is MarkCount, is Mastery -> Color.Unspecified

        is TankType -> {
            when (this) {
                is TankType.Common -> MaterialTheme.colorScheme.tankTypesColors.common
                is TankType.Premium -> MaterialTheme.colorScheme.tankTypesColors.premium
                is TankType.Premiumized -> MaterialTheme.colorScheme.tankTypesColors.premiumized
                is TankType.Collection -> MaterialTheme.colorScheme.tankTypesColors.collection
            }
        }

        is Premium -> {
            when (this) {
                is Premium.Common -> MaterialTheme.colorScheme.tankTypesColors.common
                is Premium.IsPremium -> MaterialTheme.colorScheme.tankTypesColors.premium
            }
        }

        else -> {
            if (selected) MaterialTheme.colorScheme.onBackground
            else MaterialTheme.colorScheme.onPrimary
        }
    }
}

@Composable
fun ItemStatus<*>.useColorFilter(): Boolean {
    return when (this) {
        is Nation, is Experience, is MarkCount, is Mastery -> false
        else -> true
    }
}

@Composable
fun ItemStatus<*>.borderColor(): Color {
    return if (this is Nation) Color.Transparent
    else MaterialTheme.colorScheme.onPrimary
}

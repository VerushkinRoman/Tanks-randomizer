package com.posse.tanksrandomizer.feature_online_pane.domain.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.SwitchItem

object OnlineFilterObjects {
    sealed interface Mastery : ItemStatus<Mastery> {
        data class Status(
            override val name: String = "Status",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Mastery {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class MasterySwitch(
            override val name: String = "MasterySwitch",
            override val sort: Int = Int.MAX_VALUE,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Mastery, SwitchItem {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                Status(),
                MasterySwitch(),
            ).sortedBy { it.sort }
        }
    }
}
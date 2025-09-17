package com.posse.tanksrandomizer.feature_online_screen.domain.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.SwitchItem

object OnlineFilterObjects {
    sealed interface Mastery : ItemStatus<Mastery> {
        data class None(
            override val name: String = "class none",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Mastery {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Class3(
            override val name: String = "class 3",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Mastery {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Class2(
            override val name: String = "class 2",
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Mastery {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Class1(
            override val name: String = "class 1",
            override val sort: Int = 4,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Mastery {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class Master(
            override val name: String = "Master",
            override val sort: Int = 5,
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
                None(),
                Class3(),
                Class2(),
                Class1(),
                Master(),
                MasterySwitch(),
            ).sortedBy { it.sort }
        }
    }

    sealed interface Premium : ItemStatus<Premium> {
        data class Common(
            override val name: String = "Common",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Premium {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class IsPremium(
            override val name: String = "IsPremium",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Premium {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        data class PremiumSwitch(
            override val name: String = "PremiumSwitch",
            override val sort: Int = Int.MAX_VALUE,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Premium, SwitchItem {
            override fun copy(selected: Boolean, random: Boolean) =
                copy(selected = selected, random = random, sort = sort)
        }

        companion object {
            val defaultValues = listOf(
                Common(),
                IsPremium(),
                PremiumSwitch(),
            ).sortedBy { it.sort }
        }
    }
}
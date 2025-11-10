package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import kotlinx.serialization.Serializable

object OnlineFilterObjects {
    @Serializable
    sealed interface Mastery : ItemStatus<Mastery> {
        @Serializable
        data class None(
            override val name: String = "class none",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Mastery {
            override fun copy(selected: Boolean, random: Boolean) =
                None(
                    selected = selected,
                    random = random,
                    sort = sort
                )
        }

        @Serializable
        data class Class3(
            override val name: String = "class 3",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Mastery {
            override fun copy(selected: Boolean, random: Boolean) =
                Class3(
                    selected = selected,
                    random = random,
                    sort = sort
                )
        }

        @Serializable
        data class Class2(
            override val name: String = "class 2",
            override val sort: Int = 3,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Mastery {
            override fun copy(selected: Boolean, random: Boolean) =
                Class2(
                    selected = selected,
                    random = random,
                    sort = sort
                )
        }

        @Serializable
        data class Class1(
            override val name: String = "class 1",
            override val sort: Int = 4,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Mastery {
            override fun copy(selected: Boolean, random: Boolean) =
                Class1(
                    selected = selected,
                    random = random,
                    sort = sort
                )
        }

        @Serializable
        data class Master(
            override val name: String = "Master",
            override val sort: Int = 5,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Mastery {
            override fun copy(selected: Boolean, random: Boolean) =
                Master(
                    selected = selected,
                    random = random,
                    sort = sort
                )
        }

        companion object {
            val defaultValues = listOf(
                None(),
                Class3(),
                Class2(),
                Class1(),
                Master(),
            ).sortedBy { it.sort }
        }
    }

    @Serializable
    sealed interface Premium : ItemStatus<Premium> {
        @Serializable
        data class Common(
            override val name: String = "Common",
            override val sort: Int = 1,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Premium {
            override fun copy(selected: Boolean, random: Boolean) =
                Common(
                    selected = selected,
                    random = random,
                    sort = sort
                )
        }

        @Serializable
        data class IsPremium(
            override val name: String = "IsPremium",
            override val sort: Int = 2,
            override val selected: Boolean = true,
            override val random: Boolean = false,
        ) : Premium {
            override fun copy(selected: Boolean, random: Boolean) =
                IsPremium(
                    selected = selected,
                    random = random,
                    sort = sort
                )
        }

        companion object {
            val defaultValues = listOf(
                Common(),
                IsPremium(),
            ).sortedBy { it.sort }
        }
    }
}

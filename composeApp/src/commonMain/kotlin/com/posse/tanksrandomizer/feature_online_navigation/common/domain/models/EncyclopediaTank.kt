package com.posse.tanksrandomizer.feature_online_navigation.common.domain.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.Tank

data class EncyclopediaTank(
    val id: Int,
    val isPremium: Boolean,
    val name: String,
    val nationName: String,
    val tier: Int,
    val typeName: String,
    val image: String?,
)

fun EncyclopediaTank.toTank(mastery: Int): Tank {
    return Tank(
        id = id,
        name = name,
        imageUrl = image,
        tier = tier,
        nationName = nationName,
        isPremium = isPremium,
        typeName = typeName,
        mastery = mastery
    )
}

@Suppress("SpellCheckingInspection")
object MissedEncyclopediaTanks {
    val value = listOf(
//        EncyclopediaTank(
//            id = 17265,
//            isPremium = true,
//            name = "Протей",
//            nationName = Nation.NATION_OTHER,
//            tier = 10,
//            typeName = Type.TANK_DESTROYER,
//            image = null
//        ),
//        EncyclopediaTank(
//            id = 12849,
//            isPremium = true,
//            name = "WZ-1G-P Бесстрашный",
//            nationName = Nation.NATION_CHINA,
//            tier = 8,
//            typeName = Type.TANK_DESTROYER,
//            image = null
//        ),
        EncyclopediaTank(
            id = 27905,
            isPremium = true,
            name = "КВ-4Т",
            nationName = Nation.NATION_USSR,
            tier = 8,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 29697,
            isPremium = true,
            name = "Диамант",
            nationName = Nation.NATION_USSR,
            tier = 8,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 25617,
            isPremium = true,
            name = "HWK 30",
            nationName = Nation.NATION_GERMANY,
            tier = 8,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 7521,
            isPremium = true,
            name = "Type 5 Ka-Ri",
            nationName = Nation.NATION_JAPAN,
            tier = 8,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 12417,
            isPremium = true,
            name = "Bisonte C45",
            nationName = Nation.NATION_EUROPEAN,
            tier = 8,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 12673,
            isPremium = true,
            name = "Tornvagn",
            nationName = Nation.NATION_EUROPEAN,
            tier = 8,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 28433,
            isPremium = true,
            name = "VK 75.01 (K)",
            nationName = Nation.NATION_GERMANY,
            tier = 8,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 12929,
            isPremium = false,
            name = "TNH T Vz. 51",
            nationName = Nation.NATION_EUROPEAN,
            tier = 9,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 15233,
            isPremium = false,
            name = "CS-59",
            nationName = Nation.NATION_EUROPEAN,
            tier = 9,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 18241,
            isPremium = true,
            name = "B-C Bourrasque",
            nationName = Nation.NATION_FRANCE,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 10289,
            isPremium = false,
            name = "WZ-132-1",
            nationName = Nation.NATION_CHINA,
            tier = 10,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 23633,
            isPremium = false,
            name = "FV205b Alligator",
            nationName = Nation.NATION_UK,
            tier = 10,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 29217,
            isPremium = true,
            name = "T32E4",
            nationName = Nation.NATION_USA,
            tier = 8,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 22097,
            isPremium = true,
            name = "Churchill VIII",
            nationName = Nation.NATION_UK,
            tier = 6,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 26641,
            isPremium = true,
            name = "Kpz 07 RH",
            nationName = Nation.NATION_GERMANY,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 29185,
            isPremium = true,
            name = "Т-44-122",
            nationName = Nation.NATION_USSR,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 10033,
            isPremium = false,
            name = "WZ-132A",
            nationName = Nation.NATION_CHINA,
            tier = 9,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 19777,
            isPremium = true,
            name = "A.P. AMX 30",
            nationName = Nation.NATION_FRANCE,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 10625,
            isPremium = false,
            name = "CC 1 Mk. 2",
            nationName = Nation.NATION_EUROPEAN,
            tier = 9,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 5425,
            isPremium = false,
            name = "WZ-113",
            nationName = Nation.NATION_CHINA,
            tier = 10,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 26657,
            isPremium = true,
            name = "ASTRON Rex",
            nationName = Nation.NATION_USA,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 22865,
            isPremium = true,
            name = "Charlemagne",
            nationName = Nation.NATION_UK,
            tier = 8,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 25601,
            isPremium = true,
            name = "ИС-2 экр.",
            nationName = Nation.NATION_USSR,
            tier = 7,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 10369,
            isPremium = false,
            name = "Minotauro",
            nationName = Nation.NATION_EUROPEAN,
            tier = 10,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 13617,
            isPremium = true,
            name = "BZ-176",
            nationName = Nation.NATION_CHINA,
            tier = 8,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 13425,
            isPremium = false,
            name = "ЛВ-750 Паллада",
            nationName = Nation.NATION_OTHER,
            tier = 9,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 29985,
            isPremium = true,
            name = "TL-1 LPC",
            nationName = Nation.NATION_USA,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 14977,
            isPremium = false,
            name = "CS-63",
            nationName = Nation.NATION_EUROPEAN,
            tier = 10,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 29729,
            isPremium = true,
            name = "AAT60",
            nationName = Nation.NATION_USA,
            tier = 10,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 9777,
            isPremium = true,
            name = "WZ-114",
            nationName = Nation.NATION_CHINA,
            tier = 9,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 18049,
            isPremium = false,
            name = "Wz.60",
            nationName = Nation.NATION_EUROPEAN,
            tier = 9,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 14721,
            isPremium = true,
            name = "Strv 81",
            nationName = Nation.NATION_EUROPEAN,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 7169,
            isPremium = false,
            name = "ИС-7",
            nationName = Nation.NATION_USSR,
            tier = 10,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 13361,
            isPremium = true,
            name = "116-F3",
            nationName = Nation.NATION_CHINA,
            tier = 10,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 15473,
            isPremium = false,
            name = "МБ-64 Галилей",
            nationName = Nation.NATION_OTHER,
            tier = 10,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 11825,
            isPremium = false,
            name = "BZ-75",
            nationName = Nation.NATION_CHINA,
            tier = 10,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 13105,
            isPremium = true,
            name = "WZ-113-II",
            nationName = Nation.NATION_CHINA,
            tier = 8,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 13185,
            isPremium = false,
            name = "Vz. 55",
            nationName = Nation.NATION_EUROPEAN,
            tier = 10,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 28193,
            isPremium = true,
            name = "TS-60",
            nationName = Nation.NATION_USA,
            tier = 8,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 25857,
            isPremium = true,
            name = "Об. 777 II",
            nationName = Nation.NATION_USSR,
            tier = 10,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 13169,
            isPremium = false,
            name = "ЛВ-1300 Уран",
            nationName = Nation.NATION_OTHER,
            tier = 10,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 15985,
            isPremium = false,
            name = "МБ-35 Кулибин",
            nationName = Nation.NATION_OTHER,
            tier = 9,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 12081,
            isPremium = false,
            name = "BZ-68",
            nationName = Nation.NATION_CHINA,
            tier = 9,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 12593,
            isPremium = false,
            name = "BZ-166",
            nationName = Nation.NATION_CHINA,
            tier = 8,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 20801,
            isPremium = true,
            name = "Char Mle. 75",
            nationName = Nation.NATION_FRANCE,
            tier = 9,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 12337,
            isPremium = false,
            name = "BZ-58",
            nationName = Nation.NATION_CHINA,
            tier = 7,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 20033,
            isPremium = true,
            name = "Char Futur 4",
            nationName = Nation.NATION_FRANCE,
            tier = 9,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 26113,
            isPremium = true,
            name = "Объект 452К",
            nationName = Nation.NATION_USSR,
            tier = 9,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 27425,
            isPremium = true,
            name = "TL-7-120",
            nationName = Nation.NATION_USA,
            tier = 9,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 14705,
            isPremium = true,
            name = "Спутник",
            nationName = Nation.NATION_OTHER,
            tier = 7,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 25873,
            isPremium = true,
            name = "Elefant",
            nationName = Nation.NATION_GERMANY,
            tier = 7,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 17793,
            isPremium = false,
            name = "Wz.58T",
            nationName = Nation.NATION_EUROPEAN,
            tier = 8,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 17009,
            isPremium = false,
            name = "МБ-43 Ньютон",
            nationName = Nation.NATION_OTHER,
            tier = 5,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 16241,
            isPremium = false,
            name = "МБ-73 Коперник",
            nationName = Nation.NATION_OTHER,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 28161,
            isPremium = true,
            name = "Т-V/85",
            nationName = Nation.NATION_USSR,
            tier = 6,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 17537,
            isPremium = false,
            name = "WZ.57D",
            nationName = Nation.NATION_EUROPEAN,
            tier = 7,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 20545,
            isPremium = true,
            name = "Renault G1",
            nationName = Nation.NATION_FRANCE,
            tier = 5,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 10609,
            isPremium = true,
            name = "Magnate",
            nationName = Nation.NATION_OTHER,
            tier = 7,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 16769,
            isPremium = true,
            name = "SMV CC-67",
            nationName = Nation.NATION_EUROPEAN,
            tier = 8,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 27393,
            isPremium = true,
            name = "ЛТС-85",
            nationName = Nation.NATION_USSR,
            tier = 8,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 16497,
            isPremium = false,
            name = "МБ-15 Лавлейс",
            nationName = Nation.NATION_OTHER,
            tier = 7,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 24833,
            isPremium = true,
            name = "ИС-7 Стриж",
            nationName = Nation.NATION_USSR,
            tier = 10,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 17521,
            isPremium = false,
            name = "МБ-48 Бруно",
            nationName = Nation.NATION_OTHER,
            tier = 6,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 27649,
            isPremium = true,
            name = "КВ-1С МЗ",
            nationName = Nation.NATION_USSR,
            tier = 6,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 11569,
            isPremium = true,
            name = "BZ-58-2",
            nationName = Nation.NATION_CHINA,
            tier = 9,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 23121,
            isPremium = true,
            name = "Cobra",
            nationName = Nation.NATION_UK,
            tier = 9,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 17281,
            isPremium = false,
            name = "Wz.44",
            nationName = Nation.NATION_EUROPEAN,
            tier = 6,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 15217,
            isPremium = true,
            name = "Богатырь",
            nationName = Nation.NATION_OTHER,
            tier = 7,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 29441,
            isPremium = true,
            name = "Об. 430Б",
            nationName = Nation.NATION_USSR,
            tier = 10,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 17025,
            isPremium = false,
            name = "Wz.40",
            nationName = Nation.NATION_EUROPEAN,
            tier = 5,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 27169,
            isPremium = true,
            name = "Лидер",
            nationName = Nation.NATION_USA,
            tier = 6,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 30497,
            isPremium = true,
            name = "M46 Blitz",
            nationName = Nation.NATION_USA,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 14961,
            isPremium = true,
            name = "ЛВ-426 Атлант",
            nationName = Nation.NATION_OTHER,
            tier = 8,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 18561,
            isPremium = true,
            name = "Wz.66T",
            nationName = Nation.NATION_EUROPEAN,
            tier = 8,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 27153,
            isPremium = true,
            name = "Aufkl. Panther",
            nationName = Nation.NATION_GERMANY,
            tier = 7,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 7777,
            isPremium = true,
            name = "Type 63",
            nationName = Nation.NATION_JAPAN,
            tier = 10,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 28705,
            isPremium = true,
            name = "XM66F",
            nationName = Nation.NATION_USA,
            tier = 10,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 18289,
            isPremium = true,
            name = "Биоморф",
            nationName = Nation.NATION_OTHER,
            tier = 7,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 27137,
            isPremium = true,
            name = "Об. 283",
            nationName = Nation.NATION_USSR,
            tier = 9,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 26897,
            isPremium = true,
            name = "Kpz. Pr.68 (P)",
            nationName = Nation.NATION_GERMANY,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 29473,
            isPremium = true,
            name = "AMBT",
            nationName = Nation.NATION_USA,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 14129,
            isPremium = true,
            name = "WZ-111 6",
            nationName = Nation.NATION_CHINA,
            tier = 8,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 22609,
            isPremium = true,
            name = "Caernarvon Defender",
            nationName = Nation.NATION_UK,
            tier = 8,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 31265,
            isPremium = true,
            name = "T69 Defender",
            nationName = Nation.NATION_USA,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 12401,
            isPremium = true,
            name = "Чёрный Пёс",
            nationName = Nation.NATION_OTHER,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 4913,
            isPremium = false,
            name = "59-16",
            nationName = Nation.NATION_CHINA,
            tier = 6,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 26369,
            isPremium = true,
            name = "СТ-62 вар. 2",
            nationName = Nation.NATION_USSR,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 24849,
            isPremium = true,
            name = "Kryos",
            nationName = Nation.NATION_GERMANY,
            tier = 6,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 11057,
            isPremium = true,
            name = "114 SP2",
            nationName = Nation.NATION_CHINA,
            tier = 10,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 30241,
            isPremium = true,
            name = "MBT-B",
            nationName = Nation.NATION_USA,
            tier = 10,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 13937,
            isPremium = false,
            name = "ЛВ-90 Прометей",
            nationName = Nation.NATION_OTHER,
            tier = 7,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 10865,
            isPremium = true,
            name = "Fixer",
            nationName = Nation.NATION_OTHER,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 11633,
            isPremium = true,
            name = "Чародей",
            nationName = Nation.NATION_OTHER,
            tier = 7,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 15489,
            isPremium = false,
            name = "CS-53",
            nationName = Nation.NATION_EUROPEAN,
            tier = 8,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 3889,
            isPremium = false,
            name = "WZ-132",
            nationName = Nation.NATION_CHINA,
            tier = 8,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 13681,
            isPremium = false,
            name = "ЛВ-500 Арес",
            nationName = Nation.NATION_OTHER,
            tier = 8,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 3377,
            isPremium = false,
            name = "WZ-131",
            nationName = Nation.NATION_CHINA,
            tier = 7,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 14209,
            isPremium = false,
            name = "Skoda P-JS",
            nationName = Nation.NATION_EUROPEAN,
            tier = 7,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 13697,
            isPremium = false,
            name = "TNH 105/1000",
            nationName = Nation.NATION_EUROPEAN,
            tier = 8,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 14193,
            isPremium = false,
            name = "ЛВ-7 Гиперион",
            nationName = Nation.NATION_OTHER,
            tier = 7,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 16001,
            isPremium = false,
            name = "B.U.G.I.",
            nationName = Nation.NATION_EUROPEAN,
            tier = 6,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 11393,
            isPremium = false,
            name = "Bassotto",
            nationName = Nation.NATION_EUROPEAN,
            tier = 6,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 15745,
            isPremium = false,
            name = "CS-44",
            nationName = Nation.NATION_EUROPEAN,
            tier = 7,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 13953,
            isPremium = false,
            name = "Vz. 44-1",
            nationName = Nation.NATION_EUROPEAN,
            tier = 7,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 11137,
            isPremium = false,
            name = "SMV CC-56",
            nationName = Nation.NATION_EUROPEAN,
            tier = 7,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 11649,
            isPremium = false,
            name = "Semovente M41",
            nationName = Nation.NATION_EUROPEAN,
            tier = 5,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 10881,
            isPremium = false,
            name = "SMV CC-64",
            nationName = Nation.NATION_EUROPEAN,
            tier = 8,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 14449,
            isPremium = false,
            name = "ЛВ-4 Понт",
            nationName = Nation.NATION_OTHER,
            tier = 5,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 14465,
            isPremium = false,
            name = "DS PZlnz",
            nationName = Nation.NATION_EUROPEAN,
            tier = 5,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 545,
            isPremium = false,
            name = "T1",
            nationName = Nation.NATION_USA,
            tier = 1,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 1329,
            isPremium = false,
            name = "NC-31",
            nationName = Nation.NATION_CHINA,
            tier = 1,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 3329,
            isPremium = false,
            name = "МС-1 обр. 1",
            nationName = Nation.NATION_USSR,
            tier = 1,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 3089,
            isPremium = false,
            name = "L.Tr.",
            nationName = Nation.NATION_GERMANY,
            tier = 1,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 81,
            isPremium = false,
            name = "Medium I",
            nationName = Nation.NATION_USA,
            tier = 1,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 609,
            isPremium = false,
            name = "R. Otsu",
            nationName = Nation.NATION_JAPAN,
            tier = 1,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 577,
            isPremium = false,
            name = "FT",
            nationName = Nation.NATION_FRANCE,
            tier = 1,
            typeName = Type.LIGHT_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 18305,
            isPremium = false,
            name = "Wz.70",
            nationName = Nation.NATION_EUROPEAN,
            tier = 10,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 28177,
            isPremium = true,
            name = "Kpz 07 P(E)",
            nationName = Nation.NATION_GERMANY,
            tier = 10,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 30753,
            isPremium = true,
            name = "XM57",
            nationName = Nation.NATION_USA,
            tier = 10,
            typeName = Type.TANK_DESTROYER,
            image = null
        ),
        EncyclopediaTank(
            id = 14385,
            isPremium = true,
            name = "DZT-159",
            nationName = Nation.NATION_CHINA,
            tier = 9,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 23889,
            isPremium = true,
            name = "Vickers Mk. 3",
            nationName = Nation.NATION_UK,
            tier = 10,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 30209,
            isPremium = true,
            name = "МС-11",
            nationName = Nation.NATION_USSR,
            tier = 10,
            typeName = Type.MEDIUM_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 28417,
            isPremium = true,
            name = "Об. 780",
            nationName = Nation.NATION_USSR,
            tier = 10,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
        EncyclopediaTank(
            id = 21057,
            isPremium = true,
            name = "Char Lourd",
            nationName = Nation.NATION_FRANCE,
            tier = 8,
            typeName = Type.HEAVY_TANK,
            image = null
        ),
    )
}

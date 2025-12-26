# ===============================================
# ОСНОВНЫЕ ПРАВИЛА ДЛЯ COMPOSE DESKTOP
# ===============================================

# Отключить все предупреждения для сборки
-dontwarn **

# Сохранить основные атрибуты
-keepattributes Signature,InnerClasses,EnclosingMethod,Exceptions,Annotation*

# Kotlin стандартная библиотека
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }

# JetBrains аннотации и утилиты
-keep class org.jetbrains.** { *; }

# Compose
-keep class androidx.compose.** { *; }
-keep class androidx.ui.** { *; }

# Java стандартные классы
-keep class java.** { *; }
-keep class javax.** { *; }

# Ваше приложение
-keep class com.posse.tanksrandomizer.** { *; }


# ===============================================
# СЕРИАЛИЗАЦИЯ (у вас уже есть хорошие правила)
# ===============================================

-keepclassmembers class io.ktor.** { volatile <fields>; }

-keep,includedescriptorclasses class ru.sit.android.ecard.**$$serializer { *; }

# Keep `Companion` object fields of serializable classes.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

# Keep `serializer()` on companion objects
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <2>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}


# ===============================================
# ОСТАЛЬНЫЕ ВАШИ ПРАВИЛА (можно оставить)
# ===============================================

# Специфичные правила для Compose Desktop
-keep class com.jetbrains.** { *; }
-keep class org.jetbrains.skiko.** { *; }

# JavaFX (если используется)
-keep class javafx.** { *; }

# Swing
-keep class java.awt.** { *; }
-keep class javax.swing.** { *; }

# Coroutines
-keep class kotlinx.coroutines.** { *; }

# Рефлексия
-keep class kotlin.reflect.** { *; }



-keep class org.cef.** { *; }
-keep class kotlinx.coroutines.swing.SwingDispatcherFactory



# ===============================================
# KTOR HTTP CLIENT RULES
# ===============================================

# Сохраняем все классы Ktor
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**

# Сохраняем сервисные загрузчики
-keep class kotlinx.coroutines.internal.MainDispatcherLoader
-keep class kotlinx.coroutines.internal.FastServiceLoader
-keep class kotlinx.coroutines.android.AndroidDispatcherFactory

# Сохраняем META-INF/services для ServiceLoader
-keepattributes SourceFile,LineNumberTable,*Annotation*,EnclosingMethod,Signature,InnerClasses

# Сохраняем файлы сервисов в META-INF
-keep class META-INF.services.** { *; }

# Для HTTP движков
-keep class io.ktor.client.engine.** { *; }
-keep class * implements io.ktor.client.engine.HttpClientEngineFactory {
    public <methods>;
}

# Конкретно для CIO движка
-keep class io.ktor.client.engine.cio.** { *; }
-keep class io.ktor.client.engine.cio.CIOEngineFactory { *; }

# Для корутин
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

# Сохраняем фабрики через ServiceLoader
-keepclasseswithmembers class * {
    public static ** create();
}

-keepclasseswithmembers class * {
    public static ** getInstance();
}

# ServiceLoader поддержка
-keep class * implements java.util.function.Supplier
-keep class * implements org.kodein.type.TypeToken

# Сохраняем методы, используемые через рефлексию
-keepclassmembers class * {
    @kotlin.jvm.JvmName <methods>;
}

# Сохраняем все классы BundledSQLiteDriver
-keep class androidx.sqlite.driver.bundled.BundledSQLiteDriver { *; }
-keep class androidx.sqlite.driver.bundled.BundledSQLiteDriverKt { *; }
-keep class androidx.sqlite.driver.bundled.** { *; }

# Room database
-keep class * extends androidx.room.RoomDatabase { *; }

# Entity и Dao классы
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao interface *
-keepclassmembers class * {
    @androidx.room.* *;
}

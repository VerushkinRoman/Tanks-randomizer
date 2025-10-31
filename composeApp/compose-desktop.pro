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

# Realm
-dontnote io.realm.internal.SyncObjectServerFacade

-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *

-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * { *; }

-keep class io.realm.internal.KeepMember
-keep @io.realm.internal.KeepMember class * { @io.realm.internal.KeepMember *; }

-dontwarn javax.**
-dontwarn io.realm.**
-keep class io.realm.RealmCollection
-keep class io.realm.OrderedRealmCollection

-keepclasseswithmembernames class io.realm.**

-dontnote rx.Observable

-dontnote android.security.KeyStore
-dontwarn okio.Okio
-dontwarn okio.DeflaterSink

-dontnote com.android.org.conscrypt.SSLParametersImpl
-dontnote org.apache.harmony.xnet.provider.jsse.SSLParametersImpl
-dontnote sun.security.ssl.SSLContextImpl
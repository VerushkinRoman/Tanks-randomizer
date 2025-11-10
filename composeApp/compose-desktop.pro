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

# ===============================================
# REALM RULES
# ===============================================

# Сохраняем все классы Realm
-keep class io.realm.** { *; }
-dontwarn io.realm.**

# Сохраняем нативные методы Realm
-keepclasseswithmembers class * {
    native <methods>;
}

# Сохраняем SoLoader и нативные библиотеки
-keep class io.realm.kotlin.jvm.SoLoader { *; }
-keep class io.realm.kotlin.internal.interop.** { *; }

# Сохраняем методы, используемые через рефлексию
-keepclassmembers class io.realm.kotlin.jvm.SoLoader {
    public static void load();
}

# Сохраняем все модели Realm
-keep class com.posse.tanksrandomizer.feature_online_screen.data.models.** { *; }

# Сохраняем схемы Realm
-keep class * extends io.realm.kotlin.types.RealmObject { *; }
-keep class * implements io.realm.kotlin.types.BaseRealmObject { *; }

# Сохраняем Companion объекты для моделей Realm
-keepclassmembers class ** {
    public static ** Companion;
}

# Сохраняем методы, генерируемые Realm
-keepclassmembers class * {
    public static ** io_realm_kotlin_schema(...);
}

# Сохраняем аннотации Realm
-keepattributes *Annotation*
-keep @io.realm.kotlin.types.annotations.Ignore class *
-keep @io.realm.kotlin.types.annotations.PrimaryKey class *
-keep @io.realm.kotlin.types.annotations.Index class *
-keep @io.realm.kotlin.types.annotations.RealmClass class *

# Сохраняем сервисные загрузчики для Realm
-keep class META-INF.services.io.realm.** { *; }

# Для внутренних классов Realm
-keepclassmembers class io.realm.kotlin.internal.** { *; }
plugins {
    kotlin("jvm") version "1.9.23" // ou sua versão
    application
}

group = "com.github.bot-furia-cs"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.1.0")
    implementation("io.ktor:ktor-client-core:2.3.4")
    implementation("io.ktor:ktor-client-cio:2.3.4")
    implementation("org.jsoup:jsoup:1.17.2")
    implementation("org.seleniumhq.selenium:selenium-chrome-driver:4.24.0")
    implementation("org.seleniumhq.selenium:selenium-java:4.24.0")
    implementation("org.slf4j:slf4j-simple:2.0.13")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("com.google.code.gson:gson:2.11.0")
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation ("com.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.1.0")

}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "21" // AGORA usa 21, para ficar igual ao compileJava
    }
}

application {
    mainClass.set("com.github.bot-furia-cs.MainKt")
}

plugins {
    kotlin("jvm") version "1.7.22"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

group = "ru.spliterash"
version = "1.0.2"


bukkit {
    name = "HttpCommandExecutor"
    main = "ru.spliterash.httpExecutor.HttpCommandExecutor"
    apiVersion = "1.13"
    authors = listOf("Spliterash")

    libraries = listOf("org.jetbrains.kotlin:kotlin-stdlib:1.7.22")
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/") // PaperAPI

}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")

    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:1.7.22")
}
kotlin {
    jvmToolchain(17)
}
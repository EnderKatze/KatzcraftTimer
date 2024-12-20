/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm")
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }

    maven {
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
    mavenCentral()
}

dependencies {
    api(libs.com.google.inject.guice)
    api(libs.commons.io.commons.io)
    api(libs.org.jetbrains.kotlin.kotlin.stdlib.jdk8)
    compileOnly(libs.org.spigotmc.spigot.api)
    compileOnly(libs.me.clip.placeholderapi)
    compileOnly(libs.org.projectlombok.lombok)
    implementation(kotlin("stdlib-jdk8"))

    implementation("io.github.revxrsal:lamp.common:4.0.0-beta.19")
    implementation("io.github.revxrsal:lamp.bukkit:4.0.0-beta.19")
}

group = "de.enderkatze"
version = "1.0-SNAPSHOT"
description = "KatzcraftTimer"

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
kotlin {
    jvmToolchain(8)
}
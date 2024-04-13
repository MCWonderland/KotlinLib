import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    `maven-publish`
    `java-library`
}

group = "org.mcwonderland"
version = "1.9.23b1"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()

    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")

    api(kotlin("reflect"))

    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
}


tasks {
    val shadowJar by getting(ShadowJar::class) {
        archiveBaseName.set("KotlinLib")
        archiveClassifier.set("")
        archiveVersion.set(project.version.toString())
        configurations {
            all {
                exclude("org.spigotmc:spigot-api:.*")
            }
        }
    }

    "assemble" {
        dependsOn(shadowJar)
    }
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "imanity-libraries"
            url = uri("https://repo.imanity.dev/imanity-libraries")

            credentials {
                username = ""
                password = ""
            }
        }
    }
}
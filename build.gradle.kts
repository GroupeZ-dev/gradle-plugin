plugins {
    kotlin("jvm") version "2.1.20"
    `maven-publish`
}

allprojects {
    group = "re.alwyn974.groupez"
    // x-release-please-start-version
    version = "1.0.0"
    // x-release-please-end

    repositories {
        mavenCentral()
        mavenLocal()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "java-gradle-plugin")
    apply(plugin = "maven-publish")

    kotlin {
        jvmToolchain(21)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        implementation(gradleApi())

        testImplementation(kotlin("test"))
    }

    publishing {
        repositories {
            mavenLocal()
        }
    }
}
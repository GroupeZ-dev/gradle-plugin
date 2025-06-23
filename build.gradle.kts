plugins {
    `java-gradle-plugin`
    `maven-publish`
    kotlin("jvm") version "2.1.20"
}

group = "re.alwyn974.groupez"
version = "1.0.0-SNAPSHOT"

gradlePlugin {
    plugins {
        create("groupezPlugin") {
            id = "re.alwyn974.groupez"
            implementationClass = "GroupeZRepositoryPlugin"
        }
    }
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

publishing {
    repositories {}
}
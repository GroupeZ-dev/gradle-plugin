plugins {
    `java-gradle-plugin`
    `maven-publish`
    kotlin("jvm") version "2.1.20"
}

group = "re.alwyn974.groupez"
// x-release-please-start-version
version = "1.0.0-SNAPSHOT"
// x-release-please-end

gradlePlugin {
    plugins {
        create("groupez-repository-plugin") {
            id = "re.alwyn974.groupez.repository"
            implementationClass = "re.alwyn974.groupez.repository.RepositoryPlugin"
            description = "Declare all groupez repositories"
        }
        create("groupez-publish-plugin") {
            id = "re.alwyn974.groupez.publish"
            implementationClass = "re.alwyn974.groupez.publish.PublishPlugin"
            description = "Publish artifacts to groupez repositories"
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
    repositories {
        mavenLocal()
    }
}
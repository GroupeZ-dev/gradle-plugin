import java.util.Locale

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

    java {
        withJavadocJar()
        withSourcesJar()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        implementation(gradleApi())
        implementation(gradleKotlinDsl())

        testImplementation(kotlin("test"))
    }

    publishing {
        var repository = System.getProperty("repository.name", "snapshots").replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        repositories {
            maven {
                name = "groupez${repository}"
                url = uri("https://repo.groupez.dev/${repository.lowercase()}")
                credentials {
                    username = findProperty("${name}Username") as String? ?: System.getenv("MAVEN_USERNAME")
                    password = findProperty("${name}Password") as String? ?: System.getenv("MAVEN_PASSWORD")
                }
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }

        publications {
            register<MavenPublication>("groupez${repository}") {
                pom {
                    groupId = project.group as String?
                    name = project.name
                    artifactId = name.get().lowercase()
                    version = project.version as String?

                    scm {
                        connection = "scm:git:git://github.com/GroupeZ-dev/${rootProject.name}.git"
                        developerConnection = "scm:git:ssh://github.com/GroupeZ-dev/${rootProject.name}.git"
                        url = "https://github.com/GroupeZ-dev/${rootProject.name}/"
                    }
                }
                from(components["java"])
            }
        }
    }

}

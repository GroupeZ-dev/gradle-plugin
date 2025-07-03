package re.alwyn974.groupez.publish

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.authentication.http.BasicAuthentication
import java.util.*

class PublishPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.apply(MavenPublishPlugin::class.java)
        val publishing = project.extensions.getByType(PublishingExtension::class.java)

        val publishConfig = project.extensions.create(
            "publishConfig",
            PublishPluginExtension::class.java,
            project,
            publishing
        ).apply {
            githubOwner.convention("MaxLego08")
            repositoryName.convention("snapshots")
            isRootProject.convention(false)
        }

        project.afterEvaluate {
            configureDefaultPublishing(project, publishing, publishConfig)
        }
    }

    private fun configureDefaultPublishing(
        project: Project,
        publishing: PublishingExtension,
        publishConfig: PublishPluginExtension
    ) {
        val repository = System.getProperty("repository.name", "snapshots")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        val repositoryName = "groupez$repository"
        publishConfig.repositoryName.set(repositoryName)

        if (publishing.repositories.none { it.name == repositoryName }) {
            publishing.repositories { handler ->
                handler.maven { mvn ->
                    mvn.name = repositoryName
                    mvn.url = project.uri("https://repo.groupez.dev/${repository.lowercase()}")
                    mvn.credentials { creds ->
                        creds.username = project.findProperty("${mvn.name}Username") as String? ?: System.getenv("MAVEN_USERNAME")
                        creds.password = project.findProperty("${mvn.name}Password") as String? ?: System.getenv("MAVEN_PASSWORD")
                    }
                    mvn.authentication { auth ->
                        auth.create("basic", BasicAuthentication::class.java)
                    }
                }
            }
        }

        if (publishing.publications.none { it.name == repositoryName }) {
            publishing.publications { publications ->
                publications.register(repositoryName, MavenPublication::class.java) { mavenPublication ->
                    mavenPublication.groupId = project.group as String?
                    mavenPublication.version = project.version as String?

                    mavenPublication.pom { pom ->
                        if (publishConfig.isRootProject.get())
                            pom.name.set(project.rootProject.name)
                        else
                            pom.name.set("${project.rootProject.name}-${project.name}")
                        mavenPublication.artifactId = pom.name.get().lowercase()

                        pom.scm { scm ->
                            val owner = publishConfig.githubOwner.get()
                            scm.connection.set("scm:git:git://github.com/${owner}/${project.rootProject.name}.git")
                            scm.developerConnection.set("scm:git:ssh://github.com/${owner}/${project.rootProject.name}.git")
                            scm.url.set("https://github.com/${owner}/${project.rootProject.name}/")
                        }
                    }

                    mavenPublication.artifact(project.tasks.findByName("shadowJar"))
                    project.tasks.findByName("sourcesJar")?.let { mavenPublication.artifact(it) }
                    project.tasks.findByName("javadocJar")?.let { mavenPublication.artifact(it) }
                }
            }
        }
    }
}

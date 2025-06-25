package re.alwyn974.groupez.repository

import org.gradle.api.Plugin
import org.gradle.api.Project

class RepositoryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("groupezRepository", RepositoryExtension::class.java)

        project.afterEvaluate {
            project.repositories.maven { repo ->
                repo.name = "groupezReleases"
                repo.url = project.uri("https://repo.groupez.dev/releases")
            }

            project.repositories.maven { repo ->
                repo.name = "groupezSnapshots"
                repo.url = project.uri("https://repo.groupez.dev/snapshots")
            }

            if (extension.includePrivate.get()) {
                project.repositories.maven { repo ->
                    repo.name = "groupezPrivate"
                    repo.url = project.uri("https://repo.groupez.dev/private")
                }
            }
        }

    }
}

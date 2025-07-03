package re.alwyn974.groupez.publish

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.provider.Property
import org.gradle.api.publish.PublicationContainer
import org.gradle.api.publish.PublishingExtension
import javax.inject.Inject

abstract class PublishPluginExtension @Inject constructor(
    private val project: Project,
    private val delegate: PublishingExtension
) {
    abstract val githubOwner: Property<String>
    abstract val repositoryName: Property<String>
    /**
     * Indicates if we only use the root project name for the publication.
     */
    abstract val useRootProjectName: Property<Boolean>

    init {
        githubOwner.convention("MaxLego08")
        repositoryName.convention("snapshots")
        useRootProjectName.convention(false)
    }

    // Délégation des propriétés de PublishingExtension
    val publications: PublicationContainer
        get() = delegate.publications

    val repositories: RepositoryHandler
        get() = delegate.repositories

    fun repositories(configure: RepositoryHandler.() -> Unit) {
        delegate.repositories(configure)
    }

    fun publications(configure: PublicationContainer.() -> Unit) {
        delegate.publications(configure)
    }
}

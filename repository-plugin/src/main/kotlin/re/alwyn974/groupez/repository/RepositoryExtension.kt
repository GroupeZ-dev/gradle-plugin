package re.alwyn974.groupez.repository

import org.gradle.api.provider.Property

abstract class RepositoryExtension {
    abstract val includePrivate: Property<Boolean>

    init {
        includePrivate.convention(false)
    }
}

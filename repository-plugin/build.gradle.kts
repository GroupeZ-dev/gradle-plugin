gradlePlugin {
    plugins {
        create("repository") {
            id = "re.alwyn974.groupez.repository"
            implementationClass = "re.alwyn974.groupez.repository.RepositoryPlugin"
            description = "Declare all groupez repositories"
        }
    }
}

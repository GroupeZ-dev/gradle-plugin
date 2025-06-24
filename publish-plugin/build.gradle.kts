gradlePlugin {
    plugins {
        create("publish") {
            id = "re.alwyn974.groupez.publish"
            implementationClass = "re.alwyn974.groupez.publish.PublishPlugin"
            description = "Publish artifacts to groupez repositories"
        }
    }
}

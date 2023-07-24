plugins {
    gradlebuild.docs
}

apply<JavaMetadataPlugin>()

tasks.withType<Jar> {
    from("src/main/metadata")
}

plugins {
    gradlebuild.library
}

apply<JavaMetadataPlugin>()

dependencies {
    "implementation"("commons-lang:commons-lang:2.5")
    "implementation"("commons-httpclient:commons-httpclient:3.0")
    "implementation"("commons-codec:commons-codec:1.2")
    "implementation"("org.slf4j:jcl-over-slf4j:1.7.10")
    "implementation"("com.googlecode:reflectasm:1.01")
    "testImplementation"("junit:junit:4.13")

    "implementation"(project(":project0"))
    "implementation"(project(":project1"))
    "implementation"(project(":project2"))
}

tasks.withType<Jar> {
    from("src/main/metadata")
}

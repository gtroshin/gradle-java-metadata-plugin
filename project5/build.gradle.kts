plugins {
    gradlebuild.library
}

apply<JavaMetadataPlugin>()

dependencies {
    "api"("commons-lang:commons-lang:2.5")
    "api"("commons-httpclient:commons-httpclient:3.0")
    "api"("commons-codec:commons-codec:1.2")
    "api"("org.slf4j:jcl-over-slf4j:1.7.10")
    "implementation"("com.googlecode:reflectasm:1.01")
    "testImplementation"("junit:junit:4.13")
}

tasks.withType<Jar> {
    from("src/main/metadata")
}

plugins {
    `java-library`
}

group = "org.gradle.devprod.takehome"
version = "1.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
    withJavadocJar()
    withSourcesJar()
}

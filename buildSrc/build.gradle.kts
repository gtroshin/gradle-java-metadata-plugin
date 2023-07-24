plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("org.asciidoctor:asciidoctor-gradle-jvm:3.3.2")
    implementation("com.github.javaparser:javaparser-core:3.25.3")
    implementation("com.squareup:kotlinpoet:1.11.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

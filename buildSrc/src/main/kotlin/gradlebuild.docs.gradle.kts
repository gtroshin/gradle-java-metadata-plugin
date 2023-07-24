plugins {
    id("org.asciidoctor.jvm.convert")
}

group = "org.gradle.devprod.takehome"
version = "1.0"

tasks.named("assemble") {
    dependsOn(tasks.named("asciidoctor"))
}

# Gradle `java-metadata` plugin

Gradle `java-metadata` plugin that reads all Java files and extracts the "metadata".

# Documentation

## About

The project has a [custom Gradle plugin, `JavaMetadataPlugin`, which](buildSrc/src/main/kotlin/JavaMetadataPlugin.kt) extends the Java project building by generating metadata for Java files in your project. The metadata includes the following information: "package name", "file name", and "number of lines of code", they are automatically added to the JAR files, extending in code analysis and debugging.

## Development

### Requirements:

 - JDK 11 or newer: it can be downloaded from the official Oracle website or install via a package manager like Homebrew on MacOS or apt on Ubuntu.
 - Gradle 7.2 or newer: This is the build tool used for this project. It can be downloaded from the official Gradle website or installed via a package manager.

To start developing, please clone the project and then run the plugin using the provided Gradle Wrapper:

```bash
git clone <repository-url>
cd <repository-name>
./gradlew clean build
./gradlew extractJavaMetadata # default command to run plugin
./gradlew extractJavaMetadata -Pseparator=';' # to use a semicolon as the separator

```

For testing the plugin, run [the following Bash script](test_metadata_plugin.sh) in the project root folder:

```bash
./test_metadata_plugin.sh
```

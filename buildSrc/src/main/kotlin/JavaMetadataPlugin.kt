import com.github.javaparser.*
import com.github.javaparser.ast.CompilationUnit
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.jvm.tasks.Jar
import java.io.File
import java.nio.file.Paths

/**
 * The JavaMetadataPlugin is a Gradle plugin that reads Java files and generates metadata files for each file.
 * The metadata includes information such as the package, file name, and number of lines of code.
 * The plugin also includes the generated metadata files in the JAR file of the source set.
 */
class JavaMetadataPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extractJavaMetadataTask = project.tasks.create("extractJavaMetadata", ExtractJavaMetadata::class.java)
        project.tasks.withType(Jar::class.java) {
            dependsOn(extractJavaMetadataTask)
            from("${project.buildDir}/metadata")
        }
    }
}

open class ExtractJavaMetadata : DefaultTask() {
    private val separator = project.properties.getOrDefault("separator", "\n").toString()

    @TaskAction
    fun extract() {
        val sourcePath = project.file("src/main/java")
        val destinationPath = project.file("${project.buildDir}/metadata")

        val javaParser = JavaParser()

        sourcePath.walk().forEach { file ->
            if (file.extension == "java") {
                val result = javaParser.parse(file)
                val javaFile = result.result.get()

                val relativePath = Paths.get(sourcePath.path).relativize(Paths.get(file.parentFile.path)).toString()
                val destinationFileDir = File(destinationPath, relativePath)

                destinationFileDir.mkdirs() // Ensure the directory structure exists.

                val destinationFile = File(destinationFileDir, "${file.nameWithoutExtension}.metadata")
                val metadata = extractMetadata(javaFile, file)

                destinationFile.writeText(metadata) // Write metadata to a file.
            }
        }
    }

    private fun extractMetadata(javaFile: CompilationUnit, file: File): String {
        val packageName = javaFile.packageDeclaration.get().nameAsString
        val fileName = file.name
        val linesOfCode = file.readLines().size

        return "package=$packageName${separator}fileName=$fileName${separator}linesOfCode=$linesOfCode"
    }
}

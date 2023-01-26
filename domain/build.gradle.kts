import org.springframework.boot.gradle.tasks.bundling.BootJar

val fdVersion: String by rootProject.extra
val jacksonAnnotationsVersion: String by rootProject.extra

val jar: Jar by tasks
val bootJar: BootJar by tasks

version = fdVersion

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonAnnotationsVersion")
}

bootJar.enabled = false
jar.enabled = true
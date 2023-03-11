val fdVersion: String by rootProject.extra

version = fdVersion

dependencies {
    implementation(project(":domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.liquibase:liquibase-core")

    implementation("org.springframework.cloud:spring-cloud-stream")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-rabbit")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.cloud:spring-cloud-stream-test-binder")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:rabbitmq")
    testImplementation("org.testcontainers:postgresql")

    testImplementation("com.github.tomakehurst:wiremock-jre8-standalone")
}

sourceSets {
    create("unitTest") {
        java {
            compileClasspath += sourceSets.main.get().output
            runtimeClasspath += sourceSets.main.get().output
            srcDir(file("src/test-unit/java"))
        }
    }
}

idea {
    module {
        testSourceDirs = testSourceDirs + project.sourceSets["unitTest"].java.srcDirs
    }
}

val unitTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}

configurations["unitTestRuntimeOnly"].extendsFrom(configurations.testRuntimeOnly.get())

val unitTestTask = task<Test>("unitTest") {
    description = "Runs unit tests."
    group = "verification"

    testClassesDirs = sourceSets["unitTest"].output.classesDirs
    classpath = sourceSets["unitTest"].runtimeClasspath
}

tasks.test { dependsOn(unitTestTask) }
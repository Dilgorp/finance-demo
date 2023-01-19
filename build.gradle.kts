val springCloudVersion by extra { "4.0.0" }
val fdVersion by extra { "0.0.1-SNAPSHOT" }

plugins {
    java
    id("org.springframework.boot") version "3.0.1" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
}

repositories {
    mavenCentral()
}

subprojects {
    val project = this
    apply {
        plugin("java")
        plugin("io.spring.dependency-management")
        plugin("org.springframework.boot")
    }

    group = "ru.dilgorp"

    repositories {
        mavenCentral()
    }

    dependencies {
        "implementation"("org.springframework.boot:spring-boot-starter")
        "testImplementation"("org.springframework.boot:spring-boot-starter-test")
        "implementation"("org.projectlombok:lombok")
        "annotationProcessor"("org.projectlombok:lombok")
    }

    configurations {
        "compileOnly" {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
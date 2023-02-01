val fdVersion by extra { "0.0.1-SNAPSHOT" }
val mockitoVersion by extra { "5.0.0" }
val jacksonAnnotationsVersion by extra { "2.14.1" }

plugins {
    java
    id("org.springframework.boot") version "3.0.1" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
}

repositories {
    mavenCentral()
}

subprojects {
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
        implementation(platform("org.testcontainers:testcontainers-bom:1.17.6"))
        implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2022.0.1"))

        implementation("org.springframework.boot:spring-boot-starter")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        implementation("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
    }

    configurations {
        "compileOnly" {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    configurations {
        all { exclude(module = "slf4j-log4j12") }
        all { exclude(module = "slf4j-simple") }
        all { exclude(module = "slf4j-log4j") }
        all { exclude(module = "tomcat-jdbc") }
        all { exclude(module = "tomcat-embed-el") }
        all { exclude(module = "spring-boot-starter-tomcat") }
        all { exclude(module = "org.apache.logging.log4j") }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
val fdVersion: String by rootProject.extra
val springCloudVersion: String by rootProject.extra
val mockitoVersion: String by rootProject.extra

dependencies {
    implementation(project(":domain"))

    implementation(platform("org.testcontainers:testcontainers-bom:1.17.6"))

    implementation("org.springframework.cloud:spring-cloud-stream:$springCloudVersion")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka:$springCloudVersion")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-rabbit:$springCloudVersion")

    testImplementation("org.springframework.cloud:spring-cloud-stream-test-binder:$springCloudVersion")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:kafka")
    testImplementation("org.testcontainers:rabbitmq")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
}

version = fdVersion
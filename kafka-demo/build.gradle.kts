val fdVersion: String by rootProject.extra
val springCloudVersion: String by rootProject.extra

dependencies {
    implementation(project(":domain"))

    implementation("org.springframework.cloud:spring-cloud-stream:$springCloudVersion")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka:$springCloudVersion")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-rabbit:$springCloudVersion")
    testImplementation("org.springframework.cloud:spring-cloud-stream-test-binder:$springCloudVersion")
}

version = fdVersion
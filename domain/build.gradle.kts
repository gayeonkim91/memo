plugins {
    id("io.spring.dependency-management")
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:4.0.3"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

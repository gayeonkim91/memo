plugins {
    id("io.spring.dependency-management")
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:4.0.3"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

plugins {
    java
    id("org.springframework.boot") version "4.0.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.diffplug.spotless") version "6.25.0"
    id("checkstyle")
    id("com.github.spotbugs") version "6.0.25"
}

group = "com.gayeon"
version = "0.0.1-SNAPSHOT"
description = "memo"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // SpotBugs annotations (optional but useful)
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.8.6")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
// ---------- Spotless (format) ----------
spotless {
    java {
        eclipse().configFile("config/formatter/eclipse-default-style-formatter.xml")
        // Remove unused imports + normalize style
        removeUnusedImports()
        importOrder() // default order; customize if you want
        trimTrailingWhitespace()
        endWithNewline()
        // You can also target specific source sets if needed
    }
    format("misc") {
        target("*.md", ".gitignore", ".gitattributes", "**/*.yml", "**/*.yaml")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

// ---------- Checkstyle (style rules) ----------
checkstyle {
    toolVersion = "10.17.0"
    isIgnoreFailures = false
    // config file path below
    configFile = file("$rootDir/config/checkstyle/checkstyle.xml")
}

// Make checkstyle apply to main/test
tasks.withType<Checkstyle> {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

// ---------- SpotBugs (bug patterns) ----------
spotbugs {
    toolVersion.set("4.8.6")
    ignoreFailures.set(false)
}

tasks.withType<com.github.spotbugs.snom.SpotBugsTask> {
    reports {
        // HTML is easiest to read in CI artifacts
        create("html") {
            required.set(true)
            outputLocation.set(layout.buildDirectory.file("reports/spotbugs/${name}.html"))
        }
        create("xml") {
            required.set(false)
        }
    }
}

// ---------- One command quality gate ----------
tasks.named("check") {
    dependsOn("spotlessCheck")
}

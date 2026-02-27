import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.tasks.testing.Test

plugins {
    id("org.springframework.boot") version "3.5.7" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    id("com.diffplug.spotless") version "6.25.0" apply false
}

group = "com.gayeon"
version = "0.0.1-SNAPSHOT"
description = "memo"

allprojects {
    repositories { mavenCentral() }
}

subprojects {
    // core plugins
    pluginManager.apply("java")
    pluginManager.apply("checkstyle")
    pluginManager.apply("com.diffplug.spotless")

    // ----- Java toolchain -----
    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    // ----- Tests -----
    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }

    // ----- Spotless -----
    extensions.configure<SpotlessExtension> {
        java {
            eclipse().configFile("$rootDir/config/formatter/eclipse-default-style-formatter.xml")
            removeUnusedImports()
            importOrder()
            trimTrailingWhitespace()
            endWithNewline()
        }
        format("misc") {
            target("*.md", ".gitignore", ".gitattributes", "**/*.yml", "**/*.yaml")
            trimTrailingWhitespace()
            endWithNewline()
        }
    }

    // ----- Checkstyle -----
    extensions.configure<CheckstyleExtension> {
        toolVersion = "10.17.0"
        isIgnoreFailures = false
        configFile = file("$rootDir/config/checkstyle/checkstyle.xml")
    }

    tasks.withType<Checkstyle>().configureEach {
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
    }

    // ----- Quality gate -----
    tasks.named("check") {
        dependsOn("spotlessCheck")
    }
}

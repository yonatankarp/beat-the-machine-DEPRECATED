plugins {
    id("jacoco")
    id("beat-the-machine.java-conventions") apply true
    id("beat-the-machine.code-metrics") apply true
    id("beat-the-machine.publishing-conventions") apply true
    id("com.diffplug.spotless") version "6.11.0" apply true
    id("org.springframework.boot") version "2.7.4" apply false
    id("io.spring.dependency-management") version "1.0.14.RELEASE" apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.10" apply false
    id("org.jetbrains.kotlin.plugin.spring") version "1.7.10" apply false
    id("org.sonarqube") version "3.4.0.2513" apply false
}

subprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://packages.confluent.io/maven/") }
        maven {
            url = uri("https://maven.pkg.github.com/yonatankarp/beat-the-machine")
            credentials {
                username = findProperty("gpr.user")?.toString() ?: System.getenv("GITHUB_ACTOR")
                password = findProperty("gpr.key")?.toString() ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

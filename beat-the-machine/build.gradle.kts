plugins {
    id("jacoco")
    id("beat-the-machine.code-metrics")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.spring")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.mockito:mockito-core")
    }
    testImplementation("io.mockk:mockk:1.14.4")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
}

tasks {
    getByName<Jar>("jar") {
        enabled = false
    }

    build {
        finalizedBy(spotlessApply)
    }

    withType<Test> {
        useJUnitPlatform()
        finalizedBy(spotlessApply)
        finalizedBy(jacocoTestReport)
        finalizedBy(pmdTest)
    }
}

tasks.findByName("spotlessKotlin")?.dependsOn("compileKotlin")
tasks.findByName("spotlessKotlin")?.dependsOn("compileTestKotlin")
tasks.findByName("spotlessKotlin")?.dependsOn("test")
tasks.findByName("spotlessKotlin")?.dependsOn("jacocoTestReport")

/* Required for deployment to Railway - see: https://medium.com/codex/deploying-a-kotlin-app-in-railway-a-slack-bot-f1d7a2386652 */
task("stage") {
    dependsOn("shadowJar")
}

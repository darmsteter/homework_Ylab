plugins {
    application
}

val versions = mapOf(
    "junitJupiter" to "5.8.1",
    "mockitoCore" to "3.11.2",
    "mockitoJunitJupiter" to "3.11.2"
)

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:${versions["junitJupiter"]}")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher:${versions["junitJupiter"]}")

    testImplementation("org.mockito:mockito-core:${versions["mockitoCore"]}")
    testImplementation("org.mockito:mockito-junit-jupiter:${versions["mockitoJunitJupiter"]}")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "com.coworking_service.Main"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

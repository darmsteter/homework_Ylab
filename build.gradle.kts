plugins {
    application
}

dependencies {
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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

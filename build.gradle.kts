plugins {
    id("java")
    id("org.graalvm.python") version "24.1.1"
}

group = "test.x"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.graalvm.polyglot:polyglot:24.1.1")
    implementation("org.graalvm.polyglot:python-community:24.1.1")
    implementation("org.graalvm.polyglot:llvm-community:24.1.1")


    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}


graalPy {
    packages.set(listOf("openpyxl==3.1.5"))
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
        outputs.upToDateWhen { false }
    }
}
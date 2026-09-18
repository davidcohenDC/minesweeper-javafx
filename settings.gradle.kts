plugins {
    // Auto-provisions the JDK required by the toolchain declared in build.gradle.kts,
    // so a fresh clone builds with nothing but `./gradlew` and any JDK able to run Gradle.
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

rootProject.name = "minesweeper"

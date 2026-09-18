/*
 * Minesweeper — OOP course project, University of Bologna, a.y. 2019/2020.
 *
 * The source code is frozen as it was delivered in April 2020. This build script
 * was modernised afterwards (Gradle 8, Maven Central, JavaFX 21, Java 21 toolchain)
 * only so that the project can still be built and run from a fresh clone.
 */
plugins {
    java
    application
    // Produces a single runnable jar (build/libs/minesweeper-<version>-all.jar) via the "shadowJar" task.
    id("com.gradleup.shadow") version "8.3.6"
}

group = "it.unibo.oop19"
// The version lives in gradle.properties; semantic-release overrides it with -Pversion=<x.y.z>.

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

// JavaFX modules actually used by the application.
val javaFxVersion = "21.0.6"
val javaFxModules = listOf("base", "graphics", "controls", "fxml")
// Native libraries for every platform are bundled so the same jar runs everywhere.
val javaFxPlatforms = listOf("linux", "mac", "mac-aarch64", "win")

dependencies {
    for (platform in javaFxPlatforms) {
        for (module in javaFxModules) {
            implementation("org.openjfx:javafx-$module:$javaFxVersion:$platform")
        }
    }

    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    // Launcher (not Main) so the app can start from a plain classpath jar without the module system.
    mainClass.set("application.Launcher")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
    // The application (and its tests) read and write ~/.minesweeper. Point "user.home" to a
    // throw-away directory so tests never touch the real one and every run starts clean.
    val testHome = layout.buildDirectory.dir("test-home")
    systemProperty("user.home", testHome.get().asFile.absolutePath)
    // Lets the TestHomeSetup extension (src/test) populate that directory before any test class runs.
    systemProperty("junit.jupiter.extensions.autodetection.enabled", "true")
    doFirst {
        // LoadDataImpl uses File.mkdir() (not mkdirs()), so the parent must exist.
        testHome.get().asFile.deleteRecursively()
        testHome.get().asFile.mkdirs()
    }
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

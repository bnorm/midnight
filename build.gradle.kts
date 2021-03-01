import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.31"
    id("com.bnorm.robocode") version "0.1.0"
}

version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf(
            "-Xopt-in=kotlin.ExperimentalStdlibApi",
            "-Xopt-in=kotlin.time.ExperimentalTime"
        )
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.robocodeRun {
    jvmArgs = (jvmArgs ?: emptyList()) + listOf(
        // "-DNOSECURITY=true", // Add if you want to work around Robocode's SecurityManager
        "-Ddebug=true",
        "-Dsun.io.useCanonCaches=false"
    )
}

robocode {
    robots {
        register("TemplateRobot") {
            classPath = "template.TemplateRobot"
            version = project.version.toString()
        }
    }
}

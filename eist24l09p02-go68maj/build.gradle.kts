plugins {
    id("java")
    id("org.openjfx.javafxplugin") version "0.1.0"
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}
application {
    mainClass.set("de.tum.in.ase.eist.App")
}
tasks.build {
    dependsOn("shadowJar")
}

tasks.jar {
    enabled = false
}

group = "de.tum.in.ase.eist"
version = "1.0.0"

repositories {
    mavenCentral()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

javafx {
    version = "22"
    modules("javafx.controls")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.logging.log4j:log4j-api:2.23.1")
    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
}

tasks.test {
    useJUnitPlatform()
}


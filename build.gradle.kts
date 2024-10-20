plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "de.hhn.aib3"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.11.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}


// Create a shadow JAR with all dependencies
tasks {
    shadowJar {
        archiveBaseName.set("app")              // Set JAR's base name
        archiveClassifier.set("")               // Set no classifier to replace the default JAR
        archiveVersion.set(version.toString())  // Include version in JAR name
        manifest {
            attributes["Main-Class"] = "de.hhn.aib3.Main" // Main class
        }
    }
}
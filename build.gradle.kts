plugins {
    java
    `maven-publish`
}

group = "com.vendoau"
version = "1.0.0"

repositories {
    mavenCentral()

    maven("https://jitpack.io")
}

dependencies {
    compileOnly("com.github.Minestom:Minestom:529_extension_improvement-SNAPSHOT")
    compileOnly("net.kyori:adventure-text-minimessage:4.10.1")
    compileOnly("net.luckperms:api:5.4")
}

tasks {
    processResources {
        filesMatching("META-INF/extension.json") {
            expand(project.properties)
        }
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
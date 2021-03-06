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
    compileOnly("com.github.Minestom:Minestom:4eec3d10a3")
    compileOnly("org.spongepowered:configurate-hocon:4.1.2")
    compileOnly("net.kyori:adventure-text-minimessage:4.11.0")
    compileOnly("net.luckperms:api:5.4")
    compileOnly("com.google.guava:guava:31.1-jre")
    compileOnly("redis.clients:jedis:4.2.3")
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
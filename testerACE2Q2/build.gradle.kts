plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation ("org.slf4j:slf4j-api:2.0.0-alpha1")
    testImplementation ("org.slf4j:slf4j-simple:2.0.0-alpha1")
}

tasks.test {
    useJUnitPlatform()
}
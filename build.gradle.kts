plugins {
    `java-library`
    id("net.minecrell.licenser") version "0.4.1"
    `maven-publish`
}

group = "eu.mikroskeem"
version = "0.0.1-SNAPSHOT"

val checkerQualVersion = "3.1.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.checkerframework:checker-qual:$checkerQualVersion")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

license {
    header = rootProject.file("etc/HEADER")
    filter.include("**/*.java")
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allJava)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "eu.mikroskeem"
            artifactId = "eztimeformat"

            from(components["java"])
            artifact(sourcesJar)
        }
    }
    repositories {
        mavenLocal()
        if (rootProject.hasProperty("wutee.repository.deploy.username") && rootProject.hasProperty("wutee.repository.deploy.password")) {
            maven("https://repo.wut.ee/repository/mikroskeem-repo") {
                credentials {
                    username = rootProject.properties["wutee.repository.deploy.username"]!! as String
                    password = rootProject.properties["wutee.repository.deploy.password"]!! as String
                }
            }
        }
    }
}

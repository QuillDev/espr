import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version ("7.1.0")
    kotlin("jvm") version "1.5.31"
}

group = "moe.quill"
version = "0.0.1"

repositories {
    mavenCentral()

    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")

    //Custom Feather Repo
    maven("https://maven.pkg.github.com/QuillDev/feather") {
        credentials {
            username = System.getenv("USERNAME")
            password = System.getenv("TOKEN")
        }
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("moe.quill:feather:0.1.0")

}


tasks {
    shadowJar {
        archiveClassifier.set("shadow")
        manifest {
            attributes["Main-Class"] = "moe.quill.espr.ESPR"

        }
    }
    jar {
        archiveClassifier.set("gamer")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes["Main-Class"] = "moe.quill.espr.ESPR"
        }
        from(configurations.runtimeClasspath.get()
            .onEach { println("add from dependencies: ${it.name}") }
            .map { if (it.isDirectory) it else zipTree(it) })
        val sourcesMain = sourceSets.main.get()
        sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
        from(sourcesMain.output)
    }
}


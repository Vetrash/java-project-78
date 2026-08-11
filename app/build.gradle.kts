plugins {
    //id("java")
    id("com.github.ben-manes.versions") version "0.53.0"
    checkstyle
    jacoco
    id("org.sonarqube") version "7.2.3.7755"

}

dependencyLocking {
    lockAllConfigurations()
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val jacksonVersion = "2.17.2"
val picocliVersion = "4.7.7"
val assertjVersion = "3.27.6"

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("info.picocli:picocli:$picocliVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
}

application { mainClass.set("hexlet.code.App") }



sonar {
    properties {
        property("sonar.projectKey", "Vetrash_java-project-78")
        property("sonar.organization", "vetrash")
    }
}

jacoco {
    toolVersion = "0.8.14"
}

tasks.test {
    useJUnitPlatform()

    finalizedBy(tasks.jacocoTestReport)
}

tasks.getByName("run", JavaExec::class) {
    standardInput = System.`in`
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
}
tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.jacocoTestReport)
}


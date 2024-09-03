import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "2.0.20"
  idea
}

val projectJdkVersion: String = JavaVersion.VERSION_17.toString()
val projectJavaLanguageVersion: JavaLanguageVersion = JavaLanguageVersion.of(projectJdkVersion)

idea {
  project {
    jdkName = projectJdkVersion
    languageLevel = IdeaLanguageLevel(projectJdkVersion)
  }
  module.name = "snils-generator"
}

group = "org.tum"
version = "1.0"

repositories {
  mavenCentral()
}

val kotlinVersion: String by project
val junitJupiterVersion: String by project
val mockkVersion: String by project

dependencies {
  implementation(kotlin("stdlib"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
  implementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
  implementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
  implementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
  testImplementation("io.mockk:mockk:$mockkVersion")
}

tasks {
  withType<KotlinCompile> { compilerOptions { jvmTarget.set(JVM_17) } }
}

tasks.withType<Test> {
  useJUnitPlatform()
  systemProperties.putAll(project.gradle.startParameter.systemPropertiesArgs)
}
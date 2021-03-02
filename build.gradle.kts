plugins {
  kotlin("jvm") version "1.4.30"
  idea
}

idea {
  project {
    jdkName = "1.8"
    languageLevel = org.gradle.plugins.ide.idea.model.IdeaLanguageLevel("1.8")
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
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
  systemProperties.putAll(project.gradle.startParameter.systemPropertiesArgs)
}
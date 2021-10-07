plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.31" 

    application 
}

repositories {
    mavenCentral() 
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom")) 

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8") 

    implementation("com.google.guava:guava:30.1-jre") 

    testImplementation("org.jetbrains.kotlin:kotlin-test") 

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit") 
}

application {
    mainClass.set("demo.AppKt") 
}

task createStartScripts(type: CreateStartScripts) {
  outputDir = file('build/sample')
  mainClass = 'org.gradle.test.Main'
  applicationName = 'myApp'
  classpath = files('path/to/some.jar')
}

tasks.test {
    useJUnitPlatform() 
}
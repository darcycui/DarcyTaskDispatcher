plugins {
    kotlin("jvm") version "2.0.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/google")
    maven("https://maven.aliyun.com/repository/jcenter")
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // 添加协程库
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    // 如果需要在测试中使用协程，也可以添加测试专用的协程库
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
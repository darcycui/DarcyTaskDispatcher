plugins {
//    kotlin("jvm") version "1.4.0"
    kotlin("jvm") version "2.2.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/google")
    maven("https://maven.aliyun.com/repository/jcenter")
    maven("https://maven.aliyun.com/repository/gradle-plugin")
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // 添加协程库
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    // 如果需要在测试中使用协程，也可以添加测试专用的协程库
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

//configure<JavaPluginConvention> {
//    sourceCompatibility = JavaVersion.VERSION_17
//}
//tasks {
//    compileKotlin {
//        kotlinOptions.jvmTarget = "1.8"
//    }
//    compileTestKotlin {
//        kotlinOptions.jvmTarget = "1.8"
//    }
//}
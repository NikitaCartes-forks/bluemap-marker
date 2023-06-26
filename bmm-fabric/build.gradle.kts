plugins {
    `kotlin-script`
    `core-script`
    `fabric-script`
    `adventure-script`
    `shadow-script`
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation(project(":bmm-core"))
    include(project(":bmm-core"))
    modImplementation("net.silkmc:silk-commands:1.9.2")
    modImplementation("net.fabricmc:fabric-loader:0.14.11")
    modImplementation(include("net.kyori:adventure-platform-fabric:5.5.2")!!)
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.76.0+1.19.2")
    modImplementation("net.fabricmc:fabric-language-kotlin:1.9.6+kotlin.1.8.22")
    modImplementation(include("me.lucko", "fabric-permissions-api", "0.2-SNAPSHOT"))
}
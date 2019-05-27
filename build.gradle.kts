plugins {
    kotlin("jvm") version "1.3.21"
}

allprojects {
    version = "1.0-SNAPSHOT"
    
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib", "1.3.21"))
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.21")
        testImplementation("junit:junit:4.12")
    }
}

project(":building-graph") {
    dependencies {
        implementation(project(":model"))
    }
}

project(":establishing-connections") {
    dependencies {
        implementation(project(":model"))
    }
}

dependencies {
    implementation(project(":model"))
    implementation(project(":building-graph"))
    implementation(project(":establishing-connections"))
}
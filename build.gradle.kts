plugins {
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "su.tomcat"
version = "0.0.1-SNAPSHOT"


java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

val springVer = "3.3.3"
val lombokVer = "1.18.34"
val postgresqlVer = "42.7.4"
val dotenvJavaVer = "3.0.2"
val mapstructVer = "1.6.0"
val hibernateValidatorVer = "8.0.1.Final"
val jakartaValidationVer = "3.1.0"
val liquibaseVer = "4.29.2"
val jjwtVer = "0.12.6"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springVer")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc:$springVer")
	implementation("org.springframework.boot:spring-boot-starter-security:$springVer")
	implementation("org.springframework.boot:spring-boot-starter-web:$springVer")
	developmentOnly("org.springframework.boot:spring-boot-devtools:$springVer")
	implementation("org.springframework.boot:spring-boot-configuration-processor:$springVer")

//	jjwt
	implementation ("io.jsonwebtoken:jjwt-api:$jjwtVer")
	implementation ("io.jsonwebtoken:jjwt-impl:$jjwtVer")
	implementation ("io.jsonwebtoken:jjwt-jackson:$jjwtVer")


	runtimeOnly("org.postgresql:postgresql:$postgresqlVer")
	implementation("io.github.cdimascio:dotenv-java:$dotenvJavaVer")
	compileOnly("org.projectlombok:lombok:$lombokVer")
	annotationProcessor("org.projectlombok:lombok")
	implementation("org.mapstruct:mapstruct:$mapstructVer")
	annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVer")
	implementation("org.hibernate.validator:hibernate-validator:$hibernateValidatorVer")
	implementation("jakarta.validation:jakarta.validation-api:$jakartaValidationVer")
//	implementation("org.liquibase:liquibase-core:$liquibaseVer")


	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")


}

tasks.withType<Test> {
	useJUnitPlatform()
}

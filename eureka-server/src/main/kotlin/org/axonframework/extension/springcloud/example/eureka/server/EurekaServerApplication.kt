package org.axonframework.extension.springcloud.example.eureka.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

/**
 * Simple Spring Boot application which is enabled as Eureka server through the [EnableEurekaServer] annotation.
 */
fun main(args: Array<String>) {
    runApplication<EurekaServerApplication>(*args)
}

@EnableEurekaServer
@SpringBootApplication
class EurekaServerApplication
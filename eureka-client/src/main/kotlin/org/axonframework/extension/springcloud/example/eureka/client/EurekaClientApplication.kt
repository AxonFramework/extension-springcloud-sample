package org.axonframework.extension.springcloud.example.eureka.client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

/**
 * Simple Spring Boot application which is enabled as Eureka client through the [EnableDiscoveryClient] annotation.
 * Scans for components in the [org.axonframework.extension.springcloud.example.giftcard] package.
 */
fun main(args: Array<String>) {
    runApplication<EurekaClientApplication>(*args)
}

@EnableDiscoveryClient
@SpringBootApplication(
    scanBasePackages = [
        "org.axonframework.extension.springcloud.example.giftcard",
        "org.axonframework.extension.springcloud.example.eureka.client"
    ]
)
class EurekaClientApplication

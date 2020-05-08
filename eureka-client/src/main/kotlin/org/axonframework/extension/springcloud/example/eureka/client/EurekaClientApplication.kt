package org.axonframework.extension.springcloud.example.eureka.client

import org.axonframework.commandhandling.distributed.RoutingStrategy
import org.axonframework.extensions.springcloud.DistributedCommandBusProperties
import org.axonframework.extensions.springcloud.commandhandling.SpringCloudCommandRouter
import org.axonframework.extensions.springcloud.commandhandling.SpringCloudHttpBackupCommandRouter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.serviceregistry.Registration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.client.RestTemplate

/**
 * Simple Spring Boot application which is enabled as Eureka client through the [EnableDiscoveryClient] annotation.
 * Scans for components in the [org.axonframework.extension.springcloud.example.giftcard] package.
 */
fun main(args: Array<String>) {
    runApplication<EurekaClientApplication>(*args)
}

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = [
    "org.axonframework.extension.springcloud.example.giftcard",
    "org.axonframework.extension.springcloud.example.eureka.client"
])
class EurekaClientApplication

/**
 * Eureka requires a custom [SpringCloudHttpBackupCommandRouter] configuration as we need to enforce the use of HTTP
 * command discovery through the [SpringCloudHttpBackupCommandRouter.Builder.enforceHttpDiscovery] method.
 */
@Configuration
class CommandRouterConfiguration {

    @Bean
    @Primary
    fun springCloudCommandRouter(
            discoveryClient: DiscoveryClient,
            localServiceInstance: Registration,
            restTemplate: RestTemplate,
            routingStrategy: RoutingStrategy,
            distributedCommandBusProperties: DistributedCommandBusProperties
    ): SpringCloudCommandRouter {
        return SpringCloudHttpBackupCommandRouter.builder()
                .discoveryClient(discoveryClient)
                .localServiceInstance(localServiceInstance)
                .restTemplate(restTemplate)
                .routingStrategy(routingStrategy)
                .messageRoutingInformationEndpoint(distributedCommandBusProperties.springCloud.fallbackUrl)
                .contextRootMetadataPropertyName(
                        distributedCommandBusProperties.springCloud.contextRootMetadataPropertyName
                )
                .enforceHttpDiscovery()
                .build()
    }
}
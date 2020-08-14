package org.axonframework.extension.springcloud.example.giftcard.configuration

import com.thoughtworks.xstream.XStream
import org.axonframework.commandhandling.CommandBus
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.config.Configurer
import org.axonframework.eventhandling.EventBus
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.axonframework.serialization.xml.XStreamSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * Axon specific configuration. Specifies an [InMemoryEventStorageEngine] and a [CustomLoggingInterceptor].
 * The latter will be registered to the [CommandGateway], [CommandBus] and [EventBus] to automatically show the flow of
 * messages per node.
 */
@Configuration
@EnableScheduling
class AxonConfiguration {


    @Bean
    fun inMemoryEventStorageEngine(): InMemoryEventStorageEngine = InMemoryEventStorageEngine()

    @Bean
    fun loggingInterceptor() = CustomLoggingInterceptor("Axon-GiftCard-App")

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    fun configureSerializer(configurer: Configurer) {
        configurer.configureSerializer { safeXStreamSerializer() }
    }

    fun safeXStreamSerializer(): XStreamSerializer {
        val xStream = XStream()
        xStream.classLoader = AxonConfiguration::class.java.classLoader
        xStream.allowTypesByWildcard(arrayOf("org.axonframework.extension.**"))
        return XStreamSerializer.builder().xStream(xStream).build()
    }

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    fun configureLogging(loggingInterceptor: CustomLoggingInterceptor,
                         commandGateway: CommandGateway,
                         commandBus: CommandBus,
                         eventBus: EventBus) {
        commandGateway.registerDispatchInterceptor(loggingInterceptor)
        commandBus.registerHandlerInterceptor(loggingInterceptor)
        eventBus.registerDispatchInterceptor(loggingInterceptor)
    }
}


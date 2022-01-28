@file:Suppress("SpringJavaInjectionPointsAutowiringInspection")

package org.axonframework.extension.springcloud.example.giftcard.configuration

import org.axonframework.commandhandling.CommandBus
import org.axonframework.eventhandling.EventBus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration

/**
 * Configures a [CustomLoggingInterceptor] for the local segment [CommandBus]
 * and [EventBus] to automatically show the flow of messages per node.
 */
@Configuration
class LoggingConfiguration {

    @Autowired
    fun configureLoggingInterceptor(@Qualifier("localSegment") localSegment: CommandBus) {
        val loggingInterceptor = CustomLoggingInterceptor("Axon-GiftCard-App")
        localSegment.registerDispatchInterceptor(loggingInterceptor)
        localSegment.registerHandlerInterceptor(loggingInterceptor)
    }

    @Autowired
    fun configureLoggingInterceptor(eventBus: EventBus) {
        eventBus.registerDispatchInterceptor(CustomLoggingInterceptor("Axon-GiftCard-App"))
    }
}


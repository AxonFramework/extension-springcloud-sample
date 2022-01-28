package org.axonframework.extension.springcloud.example.giftcard.configuration

import com.thoughtworks.xstream.XStream
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * Axon specific configuration defining an [InMemoryEventStorageEngine] and a [XStream] instance.
 */
@Configuration
@EnableScheduling
class AxonConfiguration {

    @Bean
    fun inMemoryEventStorageEngine(): InMemoryEventStorageEngine = InMemoryEventStorageEngine()

    @Bean
    fun xStream(): XStream {
        val xStream = XStream()
        xStream.allowTypesByWildcard(arrayOf("org.axonframework.extension.**"))
        return xStream
    }
}


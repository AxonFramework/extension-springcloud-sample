package org.axonframework.extension.springcloud.example.giftcard.configuration

import org.axonframework.messaging.InterceptorChain
import org.axonframework.messaging.Message
import org.axonframework.messaging.MessageDispatchInterceptor
import org.axonframework.messaging.MessageHandlerInterceptor
import org.axonframework.messaging.unitofwork.UnitOfWork
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.function.BiFunction

/**
 * A custom [MessageDispatchInterceptor] and [MessageHandlerInterceptor], based on the
 * [org.axonframework.messaging.interceptors.LoggingInterceptor]. Main difference with the original are it's Kotlin
 * implementation and the fact it doesn't blur out the entire exception.
 */
class CustomLoggingInterceptor(
    loggerName: String
) : MessageDispatchInterceptor<Message<*>>, MessageHandlerInterceptor<Message<*>> {

    private var logger: Logger = LoggerFactory.getLogger(loggerName)

    override fun handle(messages: MutableList<out Message<*>>): BiFunction<Int, Message<*>, Message<*>> {
        return BiFunction { _: Int, message: Message<*> ->
            logger.info("Dispatched message: [{}]", message.payloadType.simpleName)
            message
        }
    }

    override fun handle(unitOfWork: UnitOfWork<out Message<*>>, interceptorChain: InterceptorChain): Any? {
        val message = unitOfWork.message
        logger.info("Handling message: [{}]", message!!.payloadType.simpleName)
        return try {
            val returnValue: Any? = interceptorChain.proceed()
            logger.info(
                "[{}] executed successfully with a [{}] return value",
                message.payloadType.simpleName,
                if (returnValue == null) "null" else returnValue.javaClass.simpleName
            )
            returnValue
        } catch (e: Exception) {
            logger.info(
                String.format("[%s] execution failed with message [%s]", message.payloadType.simpleName, e.message)
            )
            throw e
        }
    }
}
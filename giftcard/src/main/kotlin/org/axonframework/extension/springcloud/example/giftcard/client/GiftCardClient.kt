package org.axonframework.extension.springcloud.example.giftcard.client

import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.extension.springcloud.example.giftcard.coreapi.BulkIssueCardCommand
import org.axonframework.extension.springcloud.example.giftcard.coreapi.IssueCardCommand
import org.axonframework.extension.springcloud.example.giftcard.coreapi.RedeemCardCommand
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

/**
 * Client in charge of dispatching GiftCard domain specific commands.
 */
@Service
class GiftCardClient(private val commandGateway: CommandGateway) {

    private var logger: Logger = LoggerFactory.getLogger("GiftCard-Command-Scheduler")

    fun issueCard(cardId: String) {
        logger.info("Creating GiftCard with id [{}]", cardId)
        commandGateway.send<IssueCardCommand>(IssueCardCommand(cardId, 100))
    }

    fun redeemCard(cardId: String) {
        val transactionId = UUID.randomUUID().toString()
        logger.info("Redeeming 75 euros from GiftCard with id [{}]", cardId)
        commandGateway.send<RedeemCardCommand>(RedeemCardCommand(cardId, transactionId, 75))
    }

    fun redeemCardInsufficientFunds(cardId: String) {
        val transactionId = UUID.randomUUID().toString()
        logger.info("Redeeming 50 euros from GiftCard with id [{}]", cardId)
        commandGateway.send<RedeemCardCommand>(RedeemCardCommand(cardId, transactionId, 50))
                .exceptionally { t ->
                    logger.info("Expected [{}] returned with message: {}", t.javaClass, t.message)
                    null
                }
    }

    fun redeemCardNegativeAmount(cardId: String) {
        val transactionId = UUID.randomUUID().toString()
        logger.info("Redeeming -50 euros from GiftCard with id [{}]", cardId)
        commandGateway.send<RedeemCardCommand>(RedeemCardCommand(cardId, transactionId, -50))
                .exceptionally { t ->
                    logger.info("Expected [{}] returned with message: {}", t.javaClass, t.message)
                    null
                }
    }

    fun bulkIssueCard() {
        val serviceId = UUID.randomUUID().toString()
        commandGateway.send<BulkIssueCardCommand>(BulkIssueCardCommand(serviceId, 10, 100))
    }
}
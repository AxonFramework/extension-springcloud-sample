package org.axonframework.extension.springcloud.example.giftcard.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*

/**
 * A scheduled client which at set intervals will invoke methods on the [GiftCardClient]. Dispatching can be disabled
 * by setting the `dispatching.enabled` setting to `false`.
 */
@Service
class ScheduledClient(
        private val giftCardClient: GiftCardClient,
        @Value("\${dispatching.enabled:true}") private val dispatchingEnabled: Boolean
) {

    private lateinit var cardId: String

    @Scheduled(initialDelay = 5_000, fixedDelay = 30_000)
    fun issueCard() {
        if (!dispatchingEnabled) {
            return
        }

        cardId = UUID.randomUUID().toString()
        giftCardClient.issueCard(cardId)
    }

    @Scheduled(initialDelay = 10_000, fixedDelay = 30_000)
    fun redeemCard() {
        if (!dispatchingEnabled) {
            return
        }

        giftCardClient.redeemCard(cardId)
    }

    @Scheduled(initialDelay = 15_000, fixedDelay = 30_000)
    fun redeemCardInsufficientFunds() {
        if (!dispatchingEnabled) {
            return
        }

        giftCardClient.redeemCardInsufficientFunds(cardId)
    }

    @Scheduled(initialDelay = 20_000, fixedDelay = 30_000)
    fun redeemCardNegativeAmount() {
        if (!dispatchingEnabled) {
            return
        }

        giftCardClient.redeemCardNegativeAmount(cardId)
    }

    @Scheduled(initialDelay = 25_000, fixedDelay = 30_000)
    fun bulkIssueCard() {
        if (!dispatchingEnabled) {
            return
        }

        giftCardClient.bulkIssueCard()
    }
}

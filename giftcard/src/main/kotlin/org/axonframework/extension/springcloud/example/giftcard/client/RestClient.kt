package org.axonframework.extension.springcloud.example.giftcard.client

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * A [RestController] annotated client which delegates POST calls through to the [GiftCardClient].
 */
@RestController
@RequestMapping("/api")
class RestClient(private val giftCardClient: GiftCardClient) {

    @PostMapping("/issue/{cardId}")
    fun issueCard(@PathVariable("cardId") cardId: String) {
        giftCardClient.issueCard(cardId)
    }

    @PostMapping("/redeem/{cardId}")
    fun redeemCard(@PathVariable("cardId") cardId: String) {
        giftCardClient.redeemCard(cardId)
    }

    @PostMapping("/redeem/{cardId}/insufficient")
    fun redeemCardInsufficientFunds(@PathVariable("cardId") cardId: String) {
        giftCardClient.redeemCardInsufficientFunds(cardId)
    }

    @PostMapping("/redeem/{cardId}/negative")
    fun redeemCardNegativeAmount(@PathVariable("cardId") cardId: String) {
        giftCardClient.redeemCardNegativeAmount(cardId)
    }

    @PostMapping("/issue/bulk")
    fun bulkIssueCard() {
        giftCardClient.bulkIssueCard()
    }
}
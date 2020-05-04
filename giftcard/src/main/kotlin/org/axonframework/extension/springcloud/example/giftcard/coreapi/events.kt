package org.axonframework.extension.springcloud.example.giftcard.coreapi

data class CardIssuedEvent(val cardId: String, val amount: Int)
data class CardRedeemedEvent(val cardId: String, val transactionId : String, val amount: Int)
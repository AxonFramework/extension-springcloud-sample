package org.axonframework.extension.springcloud.example.giftcard.coreapi

import org.axonframework.commandhandling.RoutingKey
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class BulkIssueCardCommand(@RoutingKey val serviceId: String, val numberOfCards: Int, val amount: Int)
data class IssueCardCommand(@RoutingKey val cardId: String, val amount: Int)
data class RedeemCardCommand(@TargetAggregateIdentifier val cardId: String, val transactionId: String, val amount: Int)

package org.axonframework.extension.springcloud.example.giftcard.domain

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.extension.springcloud.example.giftcard.coreapi.CardIssuedEvent
import org.axonframework.extension.springcloud.example.giftcard.coreapi.CardRedeemedEvent
import org.axonframework.extension.springcloud.example.giftcard.coreapi.IssueCardCommand
import org.axonframework.extension.springcloud.example.giftcard.coreapi.RedeemCardCommand
import org.axonframework.modelling.command.AggregateCreationPolicy
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.modelling.command.CreationPolicy
import org.axonframework.spring.stereotype.Aggregate

/**
 * GiftCard command model, which allows creation through the [IssueCardCommand] command handler and funds retrieval
 * through the [RedeemCardCommand] command handler.
 */
@Aggregate
class GiftCard {

    @AggregateIdentifier
    private lateinit var cardId: String
    private var remainingValue: Int = 0

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.ALWAYS)
    fun handle(command: IssueCardCommand) {
        apply(CardIssuedEvent(command.cardId, command.amount))
    }

    @CommandHandler
    fun handle(command: RedeemCardCommand) {
        if (command.amount > remainingValue) {
            throw IllegalStateException("Insufficient founds remaining to redeem [" + command.amount + "]")
        }
        if (command.amount < 0) {
            throw IllegalArgumentException("Cannot redeem a negative amount like [" + command.amount + "]")
        }
        apply(CardRedeemedEvent(cardId, command.transactionId, command.amount))
    }

    @EventSourcingHandler
    fun on(event: CardIssuedEvent) {
        cardId = event.cardId
        remainingValue = event.amount
    }

    @EventSourcingHandler
    fun on(event: CardRedeemedEvent) {
        remainingValue -= event.amount
    }
}
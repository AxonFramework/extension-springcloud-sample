package org.axonframework.extension.springcloud.example.giftcard.domain

import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.extension.springcloud.example.giftcard.coreapi.BulkIssueCardCommand
import org.axonframework.extension.springcloud.example.giftcard.coreapi.IssueCardCommand
import org.springframework.stereotype.Service
import java.util.*

/**
 * External command handler in charge of dispatching several [IssueCardCommand] upon handling a [BulkIssueCardCommand].
 */
@Service
class BulkIssueCommandHandler(private val commandGateway: CommandGateway) {

    @CommandHandler
    fun handle(command: BulkIssueCardCommand) {
        for (i in 0..command.numberOfCards) {
            commandGateway.send<IssueCardCommand>(IssueCardCommand(UUID.randomUUID().toString(), command.amount))
        }
    }
}
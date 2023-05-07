package org.ldclrcq.marrtrix.infrastructure.api

import jakarta.enterprise.context.ApplicationScoped
import org.ldclrcq.marrtrix.domain.matrix.MatrixMessage
import org.ldclrcq.marrtrix.domain.matrix.MatrixNotifier
import java.util.LinkedList
import java.util.Queue

@ApplicationScoped
class MatrixNotifierFixture : MatrixNotifier {
    val sentMessages: Queue<MatrixMessage> = LinkedList()
    override suspend fun sendMessage(notification: MatrixMessage) {
        this.sentMessages.add(notification)
    }
    fun firstMessage(): MatrixMessage = sentMessages.poll()
}
package org.ldclrcq.marrtrix.domain.matrix

interface MatrixNotifier {
    suspend fun sendMessage(notification: MatrixMessage)
}
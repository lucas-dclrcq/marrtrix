package org.ldclrcq.domain.matrix

interface MatrixNotifier {
    suspend fun sendMessage(notification: MatrixMessage)
}
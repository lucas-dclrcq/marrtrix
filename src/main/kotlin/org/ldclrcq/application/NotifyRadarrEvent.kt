package org.ldclrcq.application

import com.trendyol.kediatr.Command
import com.trendyol.kediatr.CommandHandler
import io.quarkus.runtime.Startup
import jakarta.enterprise.context.ApplicationScoped
import org.ldclrcq.domain.matrix.MatrixNotifier
import org.ldclrcq.domain.radarr.RadarrNotification
import org.ldclrcq.domain.radarr.RadarrPayload

data class NotifyRadarrEvent(val radarrPayload: RadarrPayload): Command

@ApplicationScoped
@Startup
class NotifyRadarrEventHandler(private val matrixNotifier: MatrixNotifier): CommandHandler<NotifyRadarrEvent> {
    override suspend fun handle(command: NotifyRadarrEvent) {
        val matrixMessage = RadarrNotification
            .fromPayload(command.radarrPayload)
            .buildMatrixMessage()

        matrixNotifier.sendMessage(matrixMessage)
    }

}

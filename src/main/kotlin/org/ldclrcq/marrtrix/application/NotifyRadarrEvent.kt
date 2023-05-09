package org.ldclrcq.marrtrix.application

import com.trendyol.kediatr.Command
import com.trendyol.kediatr.CommandHandler
import io.quarkus.runtime.Startup
import jakarta.enterprise.context.ApplicationScoped
import org.jboss.logging.Logger
import org.ldclrcq.marrtrix.domain.matrix.MatrixNotifier
import org.ldclrcq.marrtrix.domain.radarr.webhook.RadarrEvent
import org.ldclrcq.marrtrix.domain.radarr.webhook.payload.RadarrPayload

data class NotifyRadarrEvent(val radarrPayload: RadarrPayload): Command

val LOG: Logger = Logger.getLogger(NotifyRadarrEventHandler::class.java)

@ApplicationScoped
@Startup
class NotifyRadarrEventHandler(private val matrixNotifier: MatrixNotifier): CommandHandler<NotifyRadarrEvent> {
    override suspend fun handle(command: NotifyRadarrEvent) {
        LOG.info("Handling ${command.radarrPayload.eventType} Radarr event")

        val matrixMessage = RadarrEvent
            .fromPayload(command.radarrPayload)
            .buildMatrixMessage()

        matrixNotifier.sendMessage(matrixMessage)
    }

}

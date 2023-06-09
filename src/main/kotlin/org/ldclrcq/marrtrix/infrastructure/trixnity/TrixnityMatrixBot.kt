package org.ldclrcq.marrtrix.infrastructure.trixnity

import io.ktor.http.*
import io.quarkus.arc.profile.UnlessBuildProfile
import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import kotlinx.coroutines.*
import net.folivo.trixnity.client.MatrixClient
import net.folivo.trixnity.client.fromStore
import net.folivo.trixnity.client.login
import net.folivo.trixnity.client.media.InMemoryMediaStore
import net.folivo.trixnity.client.room
import net.folivo.trixnity.client.room.message.text
import net.folivo.trixnity.client.store.repository.createInMemoryRepositoriesModule
import net.folivo.trixnity.clientserverapi.model.authentication.IdentifierType
import net.folivo.trixnity.core.model.RoomId
import org.jboss.logging.Logger
import org.ldclrcq.marrtrix.domain.MarrtrixConfiguration
import org.ldclrcq.marrtrix.domain.matrix.MatrixConfiguration
import org.ldclrcq.marrtrix.domain.matrix.MatrixMessage
import org.ldclrcq.marrtrix.domain.matrix.MatrixNotifier

val LOG: Logger = Logger.getLogger(TrixnityMatrixBot::class.java)

@UnlessBuildProfile("test")
@ApplicationScoped
class TrixnityMatrixBot(marrtrixConfiguration: MarrtrixConfiguration) : MatrixNotifier {
    private val matrixConfiguration: MatrixConfiguration
    private val scope = CoroutineScope(Dispatchers.Default)

    private lateinit var matrixClient: MatrixClient

    init {
        this.matrixConfiguration = marrtrixConfiguration.matrix()
    }

    fun onStart(@Observes event: StartupEvent) {
        LOG.info("Starting Matrix Bot")
        runBlocking {
            val repositoriesModule = createInMemoryRepositoriesModule()
            val mediaStore = InMemoryMediaStore()

            matrixClient = MatrixClient.fromStore(
                repositoriesModule = repositoriesModule,
                mediaStore = mediaStore,
                scope = scope
            ).getOrThrow() ?: MatrixClient.login(
                baseUrl = Url(matrixConfiguration.url()),
                identifier = IdentifierType.User(matrixConfiguration.username()),
                password = matrixConfiguration.password(),
                repositoriesModule = repositoriesModule,
                mediaStore = mediaStore,
                scope = scope
            ).getOrThrow()
        }

        scope.launch { matrixClient.startSync() }
    }

    fun onStop(@Observes event: ShutdownEvent) {
        LOG.info("Shutting down Matrix Bot")
        runBlocking { matrixClient.stopSync(wait = true) }
        scope.cancel()
    }

    override suspend fun sendMessage(notification: MatrixMessage) {
        matrixClient.room.sendMessage(RoomId(matrixConfiguration.roomId())) {
            text(
                body = notification.toHtmlBody(),
                format = "org.matrix.custom.html",
                formattedBody = notification.toHtmlBody()
            )
        }
    }

    suspend fun isAlive() = matrixClient.api.server.getVersions().isSuccess
}
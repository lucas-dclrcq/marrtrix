package org.ldclrcq.infrastructure.trixnity

import io.ktor.http.*
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
import org.ldclrcq.domain.matrix.MatrixConfiguration
import org.ldclrcq.domain.matrix.MatrixMessage
import org.ldclrcq.domain.matrix.MatrixNotifier

@ApplicationScoped
class TrixnityMatrixBot(private val matrixConfiguration: MatrixConfiguration) : MatrixNotifier {
    private val scope = CoroutineScope(Dispatchers.Default)

    private lateinit var matrixClient: MatrixClient

    fun onStart(@Observes event: StartupEvent) {
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
        runBlocking { matrixClient.stopSync(wait = true) }
//        scope.launch { matrixClient.stopSync() }
        scope.cancel()
    }

    override suspend fun sendMessage(notification: MatrixMessage) {
        matrixClient.room.sendMessage(RoomId(matrixConfiguration.roomId())) {
            text(
                body = notification.value,
                format = "",
                formattedBody = notification.value
            )
        }
    }
}
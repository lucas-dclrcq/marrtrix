package org.ldclrcq.marrtrix.infrastructure.api

import com.trendyol.kediatr.Mediator
import io.quarkus.security.Authenticated
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.ldclrcq.marrtrix.application.NotifyRadarrEvent
import org.ldclrcq.marrtrix.domain.radarr.webhook.payload.RadarrPayload

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/radarr")
@Authenticated
class RadarrNotificationResource(private val mediator: Mediator) {

    @POST
    suspend fun radarr(body: RadarrPayload) = mediator.send(NotifyRadarrEvent(body))
}
package org.ldclrcq.marrtrix.infrastructure.api

import io.quarkus.runtime.StartupEvent
import io.vertx.core.http.HttpServerRequest
import jakarta.enterprise.event.Observes
import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.core.UriInfo
import org.jboss.logging.Logger
import org.jboss.resteasy.reactive.server.ServerRequestFilter
import org.ldclrcq.marrtrix.domain.MarrtrixConfiguration

val LOG: Logger = Logger.getLogger(Filters::class.java)

class Filters(private val marrtrixConfiguration: MarrtrixConfiguration) {

    @ServerRequestFilter(priority = 0)
    fun logBodyFilter(info: UriInfo, request: HttpServerRequest, ctx: ContainerRequestContext) {
        if (marrtrixConfiguration.debug()) {
            request.body { buffer ->
                LOG.info("Request body: ${buffer.result()}")
            }
        }
    }

    fun onStart(@Observes startupEvent: StartupEvent) {
        if (marrtrixConfiguration.debug()) {
            LOG.info("Debug mode enabled : json payload will be logged for every incoming webhook request")
        }
    }
}
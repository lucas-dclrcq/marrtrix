package org.ldclrcq.marrtrix.infrastructure.trixnity

import io.quarkus.arc.profile.UnlessBuildProfile
import io.smallrye.health.api.AsyncHealthCheck
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.asUni
import jakarta.enterprise.context.ApplicationScoped
import kotlinx.coroutines.*
import org.eclipse.microprofile.health.HealthCheckResponse
import org.eclipse.microprofile.health.Liveness

@Liveness
@UnlessBuildProfile("test")
@ApplicationScoped
class TrixnityHealthCheck(private val trixnityMatrixBot: TrixnityMatrixBot) : AsyncHealthCheck {
    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    override fun call(): Uni<HealthCheckResponse> {
        return GlobalScope.async { this@TrixnityHealthCheck.trixnityMatrixBot.isAlive() }.asUni()
            .onItem().transform {
                val responseBuilder = HealthCheckResponse.named("Trixnity BOT health check")

                if (it) {
                    responseBuilder.up()
                }
                else {
                    responseBuilder.down()
                }

                return@transform responseBuilder.build()
            }
    }
}
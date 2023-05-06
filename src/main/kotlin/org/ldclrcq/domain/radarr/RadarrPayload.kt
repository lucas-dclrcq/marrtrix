package org.ldclrcq.domain.radarr

data class RadarrPayload(
    val movie: Movie? = null,
    val remoteMovie: RemoteMovie? = null,
    val release: Release? = null,
    val eventType: RadarrEventType? = null,
    val instanceName: String? = null,
    val applicationUrl: String? = null,
)



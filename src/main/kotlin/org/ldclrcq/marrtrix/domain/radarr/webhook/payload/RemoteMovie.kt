package org.ldclrcq.marrtrix.domain.radarr.webhook.payload

data class RemoteMovie(
    val tmdbId: Int? = null,
    val imdbId: String? = null,
    val title: String? = null,
    val year: Int? = null
)
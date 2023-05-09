package org.ldclrcq.marrtrix.domain.radarr.webhook.payload

data class Movie(
    val id: Int? = null,
    val title: String? = null,
    val year: Int? = null,
    val releaseDate: String? = null,
    val folderPath: String? = null,
    val tmdbId: Int? = null
)
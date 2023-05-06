package org.ldclrcq.domain.radarr

data class RemoteMovie(
    val tmdbId: Int? = null,
    val imdbId: String? = null,
    val title: String? = null,
    val year: Int? = null
)
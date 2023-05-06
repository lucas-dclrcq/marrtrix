package org.ldclrcq.marrtrix.domain.radarr

data class Release(
    val quality: String? = null,
    val qualityVersion: Int? = null,
    val releaseGroup: String? = null,
    val releaseTitle: String? = null,
    val indexer: String? = null,
    val size: Int? = null,
    val customFormatScore: Int? = null
)
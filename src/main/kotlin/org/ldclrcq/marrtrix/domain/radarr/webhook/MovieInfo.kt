package org.ldclrcq.marrtrix.domain.radarr.webhook

data class MovieInfo(val title: MovieTitle, val year: MovieYear)
data class MovieTitle(val value: String) {
    companion object {
        fun fromPayload(title: String?) = MovieTitle(title ?: "unknown")
    }

    override fun toString(): String {
        return this.value
    }
}

data class MovieYear(val value: String) {
    companion object {
        fun fromPayload(year: Int?) = MovieYear(year?.toString() ?: "unknown")
    }

    override fun toString(): String {
        return this.value
    }
}
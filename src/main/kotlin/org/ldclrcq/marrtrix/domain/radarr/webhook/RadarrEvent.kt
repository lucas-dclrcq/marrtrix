package org.ldclrcq.marrtrix.domain.radarr.webhook

import org.ldclrcq.marrtrix.domain.matrix.MatrixMessage
import org.ldclrcq.marrtrix.domain.radarr.webhook.payload.RadarrEventType
import org.ldclrcq.marrtrix.domain.radarr.webhook.payload.RadarrPayload

class RadarrEvent private constructor(radarrPayload: RadarrPayload) {
    private val eventType: RadarrEventType
    private val movieInfo: MovieInfo

    init {
        this.eventType = radarrPayload.eventType ?: RadarrEventType.None
        this.movieInfo = MovieInfo(
            title = MovieTitle.fromPayload(radarrPayload.movie?.title),
            year = MovieYear.fromPayload(radarrPayload.movie?.year)
        )
    }

    fun buildMatrixMessage(): MatrixMessage {
        val title = when (eventType) {
            RadarrEventType.Test -> formatMovieInfo("Test", movieInfo)
            RadarrEventType.Download -> formatMovieInfo("New movie downloaded", movieInfo)
            RadarrEventType.Grab -> formatMovieInfo("New movie grabbed", movieInfo)
            RadarrEventType.MovieAdded -> formatMovieInfo("Movie added", movieInfo)
            else -> "Event $eventType not handled"
        }

        return RadarrMatrixMessage(title)
    }

    private fun formatMovieInfo(title: String, movieInfo: MovieInfo) = """
                    🎥 $title - ${movieInfo.title} (${movieInfo.year})
                """.trimIndent()

    companion object {
        fun fromPayload(payload: RadarrPayload) = RadarrEvent(payload)
    }
}


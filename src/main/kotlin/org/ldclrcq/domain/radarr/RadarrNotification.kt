package org.ldclrcq.domain.radarr

import org.ldclrcq.domain.matrix.MatrixMessage

class RadarrNotification private constructor(private val payload: RadarrPayload) {

    fun buildMatrixMessage() = when (payload.eventType) {
        RadarrEventType.Test -> formatMovieInfo("Test", payload)
        RadarrEventType.Download -> formatMovieInfo("New movie downloaded", payload)
        RadarrEventType.Grab -> formatMovieInfo("New movie grabbed", payload)
        else -> "Event ${payload.eventType} not handled"
    }.let { MatrixMessage(it) }

    private fun formatMovieInfo(title: String, payload: RadarrPayload) = """
                    🎥 $title - ${payload.movie?.title} (${payload.movie?.year})
                    - Quality: ${payload.release?.quality}
                    - Release Date: ${payload.movie?.releaseDate}
                """.trimIndent()

    companion object {
        fun fromPayload(payload: RadarrPayload) = RadarrNotification(payload)
    }
}
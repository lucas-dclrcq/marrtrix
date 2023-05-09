package org.ldclrcq.marrtrix.domain.radarr.webhook

import org.ldclrcq.marrtrix.domain.matrix.MatrixMessage
import org.ldclrcq.marrtrix.domain.radarr.webhook.message.RadarrMatrixMessage
import org.ldclrcq.marrtrix.domain.radarr.webhook.message.RadarrMatrixMessageOverview
import org.ldclrcq.marrtrix.domain.radarr.webhook.message.RadarrMatrixMessageTitle
import org.ldclrcq.marrtrix.domain.radarr.webhook.payload.RadarrEventType
import org.ldclrcq.marrtrix.domain.radarr.webhook.payload.RadarrEventType.*
import org.ldclrcq.marrtrix.domain.radarr.webhook.payload.RadarrPayload

class RadarrEvent private constructor(radarrPayload: RadarrPayload) {
    private val eventType: RadarrEventType
    private val movieTitle: String
    private val movieYear: String
    private val movieOverview: String

    init {
        this.eventType = radarrPayload.eventType ?: None
        this.movieTitle = radarrPayload.movie?.title ?: "undefined"
        this.movieYear = radarrPayload.movie?.year?.toString() ?: "undefined"
        this.movieOverview = radarrPayload.movie?.overview ?: "No overview"
    }

    fun buildMatrixMessage(): MatrixMessage {
        val title = eventType.buildTitle(this.movieTitle, this.movieYear)
        val overview = RadarrMatrixMessageOverview(this.movieOverview )

        // build movie metadata

        // fetch tmdb info

        // download affiche and upload to matrix

        // build movie affiche

        // build movie trailer

        // build download link

        return RadarrMatrixMessage(title, overview)
    }

    private fun RadarrEventType.buildTitle(movieTitle: String, movieYear: String): RadarrMatrixMessageTitle {
        return when (this) {
            Test -> formatMessageTitle("Test", movieTitle, movieYear)
            Download -> formatMessageTitle("New movie downloaded", movieTitle, movieYear)
            Grab -> formatMessageTitle("New movie grabbed", movieTitle, movieYear)
            MovieAdded -> formatMessageTitle("Movie added", movieTitle, movieYear)
            else -> "Event $eventType not handled"
        }.let { RadarrMatrixMessageTitle(it) }
    }

    private fun formatMessageTitle(radarrActionTitle: String, movieTitle: String, movieYear: String) = """
                    🎥 $radarrActionTitle - $movieTitle ($movieYear)
                """.trimIndent()

    companion object {
        fun fromPayload(payload: RadarrPayload) = RadarrEvent(payload)
    }
}


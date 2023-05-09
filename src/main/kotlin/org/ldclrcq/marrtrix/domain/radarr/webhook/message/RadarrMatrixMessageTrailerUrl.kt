package org.ldclrcq.marrtrix.domain.radarr.webhook.message

import org.ldclrcq.marrtrix.domain.matrix.MatrixHtmlMessagePart

data class RadarrMatrixMessageTrailerUrl(val url: String): MatrixHtmlMessagePart {
    override fun toHtml() = """
<h5>Trailer</h5>
<a>${url}</a>
    """.trimIndent()

}
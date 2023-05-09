package org.ldclrcq.marrtrix.domain.radarr.webhook.message

import org.ldclrcq.marrtrix.domain.matrix.MatrixHtmlMessagePart

data class RadarrMatrixMessageOverview(val overview: String): MatrixHtmlMessagePart {
    override fun toHtml() = """
        <h5>Overview</h5>
        $overview
    """.trimIndent()

}
package org.ldclrcq.marrtrix.domain.radarr.webhook.message

import org.ldclrcq.marrtrix.domain.matrix.MatrixMessage

data class RadarrMatrixMessage(val title: RadarrMatrixMessageTitle, val overview: RadarrMatrixMessageOverview) : MatrixMessage {
    override fun toHtmlBody() = buildString {
        append(title.toHtml())
        breakLine()
        append(overview.toHtml())
    }

    private fun StringBuilder.breakLine(): StringBuilder = this.append("<br/>")
}


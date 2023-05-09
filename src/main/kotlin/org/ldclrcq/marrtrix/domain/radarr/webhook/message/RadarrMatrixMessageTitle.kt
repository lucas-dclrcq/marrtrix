package org.ldclrcq.marrtrix.domain.radarr.webhook.message

import org.ldclrcq.marrtrix.domain.matrix.MatrixHtmlMessagePart

data class RadarrMatrixMessageTitle(val value: String) : MatrixHtmlMessagePart {
    override fun toHtml() = value
}
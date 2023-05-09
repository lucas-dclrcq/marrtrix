package org.ldclrcq.marrtrix.domain.radarr.webhook

import org.ldclrcq.marrtrix.domain.matrix.MatrixMessage

data class RadarrMatrixMessage(val title: String) : MatrixMessage {
    override fun toBody() = buildString {
        append(title)
    }

}
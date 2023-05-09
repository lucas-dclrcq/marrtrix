package org.ldclrcq.marrtrix.domain

import io.smallrye.config.ConfigMapping
import org.ldclrcq.marrtrix.domain.matrix.MatrixConfiguration

@ConfigMapping(prefix = "marrtrix")
interface MarrtrixConfiguration {
    fun debug(): Boolean
    fun matrix(): MatrixConfiguration
}

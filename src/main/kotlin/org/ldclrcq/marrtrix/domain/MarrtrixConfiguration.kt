package org.ldclrcq.marrtrix.domain

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix = "marrtrix")
interface MarrtrixConfiguration {
    fun debug(): Boolean
}
package org.ldclrcq.domain.matrix

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix = "matrix")
interface MatrixConfiguration {
    fun url(): String
    fun username(): String
    fun password(): String
    fun roomId(): String
}

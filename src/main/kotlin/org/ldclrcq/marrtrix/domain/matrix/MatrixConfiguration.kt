package org.ldclrcq.marrtrix.domain.matrix

interface MatrixConfiguration {
    fun url(): String
    fun username(): String
    fun password(): String
    fun roomId(): String
}
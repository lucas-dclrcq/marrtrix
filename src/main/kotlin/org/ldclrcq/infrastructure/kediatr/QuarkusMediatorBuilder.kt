package org.ldclrcq.infrastructure.kediatr

import com.trendyol.kediatr.Mediator
import com.trendyol.kediatr.MediatorBuilder
import io.quarkus.runtime.Startup
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.spi.BeanManager

@ApplicationScoped
class QuarkusMediatorBuilder {
    @ApplicationScoped
    fun kediatRBeanProvider(beanManager: BeanManager): KediatRBeanProvider {
        return KediatRBeanProvider(beanManager)
    }

    @ApplicationScoped
    @Startup
    fun mediator(kediatRBeanProvider: KediatRBeanProvider): Mediator {
        return MediatorBuilder(kediatRBeanProvider).build()
    }
}
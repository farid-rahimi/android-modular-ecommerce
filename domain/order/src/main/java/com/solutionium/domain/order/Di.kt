package com.solutionium.domain.order

import com.solutionium.data.woo.orders.orderDataModule
import org.koin.dsl.module


fun getOrderDomainModules() = setOf(orderDomainModule, orderDataModule)


val orderDomainModule = module {
    factory<GetOrderListPagingUseCase> { GetOrderListPagingUseCaseImpl(get()) }
    factory<GetLatestOrderUseCase> { GetLatestOrderUseCaseImpl(get()) }
}

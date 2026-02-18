package com.solutionium.feature.orders

import com.solutionium.domain.order.getOrderDomainModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getOrdersModules() = setOf(ordersModule) + getOrderDomainModules()

val ordersModule = module {
    viewModel { OrderListViewModel(get()) }
}

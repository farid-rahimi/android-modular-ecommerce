package com.solutionium.domain.cart

import com.solutionium.data.cart.cartRepoModule
import com.solutionium.domain.cart.impl.AddToCartUseCaseImpl
import com.solutionium.domain.cart.impl.ClearCartUseCaseImpl
import com.solutionium.domain.cart.impl.ConfirmValidationUseCaseImpl
import com.solutionium.domain.cart.impl.GetCartItemByProductUseCaseImpl
import com.solutionium.domain.cart.impl.ObserveCartUseCaseImpl
import com.solutionium.domain.cart.impl.UpdateCartItemUseCaseImpl
import com.solutionium.domain.cart.impl.ValidateCartUseCaseImpl
import org.koin.dsl.module

fun getCartDomainModules() = setOf(cartRepoModule, cartUseCaseModule)


val cartUseCaseModule = module {
    factory<AddToCartUseCase> { AddToCartUseCaseImpl(get()) }
    factory<UpdateCartItemUseCase> { UpdateCartItemUseCaseImpl(get()) }
    factory<ClearCartUseCase> { ClearCartUseCaseImpl(get()) }
    factory<ObserveCartUseCase> { ObserveCartUseCaseImpl(get()) }
    factory<GetCartItemByProductUseCase> { GetCartItemByProductUseCaseImpl(get()) }
    factory<ValidateCartUseCase> { ValidateCartUseCaseImpl(get(), get()) }
    factory<ConfirmValidationUseCase> { ConfirmValidationUseCaseImpl(get()) }
}

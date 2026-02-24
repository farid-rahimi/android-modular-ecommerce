package com.solutionium.shared.domain.cart

import com.solutionium.shared.data.cart.cartRepoModule
import com.solutionium.shared.domain.cart.impl.AddToCartUseCaseImpl
import com.solutionium.shared.domain.cart.impl.ClearCartUseCaseImpl
import com.solutionium.shared.domain.cart.impl.ConfirmValidationUseCaseImpl
import com.solutionium.shared.domain.cart.impl.GetCartItemByProductUseCaseImpl
import com.solutionium.shared.domain.cart.impl.ObserveCartUseCaseImpl
import com.solutionium.shared.domain.cart.impl.UpdateCartItemUseCaseImpl
import com.solutionium.shared.domain.cart.impl.ValidateCartUseCaseImpl
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

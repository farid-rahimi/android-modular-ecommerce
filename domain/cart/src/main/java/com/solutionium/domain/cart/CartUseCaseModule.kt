package com.solutionium.domain.cart

import com.solutionium.domain.cart.impl.AddToCartUseCaseImpl
import com.solutionium.domain.cart.impl.ClearCartUseCaseImpl
import com.solutionium.domain.cart.impl.ConfirmValidationUseCaseImpl
import com.solutionium.domain.cart.impl.GetCartItemByProductUseCaseImpl
import com.solutionium.domain.cart.impl.ObserveCartUseCaseImpl
import com.solutionium.domain.cart.impl.UpdateCartItemUseCaseImpl
import com.solutionium.domain.cart.impl.ValidateCartUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CartUseCaseModule {

    @Binds
    abstract fun bindAddToCartUseCase(impl: AddToCartUseCaseImpl): AddToCartUseCase

    @Binds
    abstract fun bindRemoveFromCartUseCase(impl: UpdateCartItemUseCaseImpl): UpdateCartItemUseCase

    @Binds
    abstract fun bindClearCartUseCase(impl: ClearCartUseCaseImpl): ClearCartUseCase

    @Binds
    abstract fun bindObserveCartUseCase(impl: ObserveCartUseCaseImpl): ObserveCartUseCase

    @Binds
    abstract fun bindGetCartItemByProductUseCase(impl: GetCartItemByProductUseCaseImpl): GetCartItemByProductUseCase

    @Binds
    abstract fun bindValidateCartUseCase(impl: ValidateCartUseCaseImpl): ValidateCartUseCase

    @Binds
    abstract fun bindConfirmValidationUseCase(impl: ConfirmValidationUseCaseImpl): ConfirmValidationUseCase
}


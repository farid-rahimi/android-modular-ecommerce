package com.solutionium.data.api.woo

import com.solutionium.data.api.woo.impl.WooCategoryRemoteSourceImpl
import com.solutionium.data.api.woo.impl.WooCheckoutRemoteSourceImpl
import com.solutionium.data.api.woo.impl.WooConfigRemoteSourceImpl
import com.solutionium.data.api.woo.impl.WooCouponRemoteSourceImpl
import com.solutionium.data.api.woo.impl.WooFavoriteRemoteSourceImpl
import com.solutionium.data.api.woo.impl.WooOrderRemoteSourceImpl
import com.solutionium.data.api.woo.impl.WooProductsRemoteSourceImpl
import com.solutionium.data.api.woo.impl.WooUserRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ApiModule {

    @Binds
    abstract fun bindWooProductsRemoteSource(
        impl: WooProductsRemoteSourceImpl
    ): WooProductsRemoteSource

    @Binds
    abstract fun bindWooCategoriesRemoteSource(
        impl: WooCategoryRemoteSourceImpl
    ): WooCategoryRemoteSource

    @Binds
    abstract fun bindWooCheckoutRemoteSource(
        impl: WooCheckoutRemoteSourceImpl
    ): WooCheckoutRemoteSource

    @Binds
    abstract fun bindWooUserRemoteSource(
        impl: WooUserRemoteSourceImpl
    ): WooUserRemoteSource

    @Binds
    abstract fun bindWooOrderRemoteSource(
        impl: WooOrderRemoteSourceImpl
    ): WooOrderRemoteSource

    @Binds
    abstract fun bindWooCouponRemoteSource(
        impl: WooCouponRemoteSourceImpl
    ): WooCouponRemoteSource

    @Binds
    abstract fun bindWooFavoriteRemoteSource(
        impl: WooFavoriteRemoteSourceImpl
    ): WooFavoriteRemoteSource

    @Binds
    abstract fun bindWooConfigRemoteSource(
        impl: WooConfigRemoteSourceImpl
    ): WooConfigRemoteSource

}
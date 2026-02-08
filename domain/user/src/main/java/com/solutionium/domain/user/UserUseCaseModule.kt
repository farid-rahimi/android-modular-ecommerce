package com.solutionium.domain.user

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserUseCaseModule {

    @Binds
    abstract fun bindSendOtpUseCase(
        impl: SendOtpUseCaseImpl
    ): SendOtpUseCase

    @Binds
    abstract fun bindCheckLoginUseCase(
        impl: CheckLoginUserUseCaseImpl
    ): CheckLoginUserUseCase

    @Binds
    abstract fun bindCheckSuperUserUseCase(
        impl: CheckSuperUserUseCaseImpl
    ): CheckSuperUserUseCase

    @Binds
    abstract fun bindLoginOrRegisterCase(
        impl: LoginOrRegisterUseCaseImpl
    ): LoginOrRegisterUseCase

    @Binds
    abstract fun bindLoginByUserPassUseCase(
        impl: LoginByUserPassUseCaseImpl
    ): LoginByUserPassUseCase

    @Binds
    abstract fun bindLogoutUseCase(
        impl: LogoutUseCaseImpl
    ): LogoutUseCase

    @Binds
    abstract fun bindGetCurrentUserUseCase(
        impl: GetCurrentUserUseCaseImpl
    ): GetCurrentUserUseCase

    @Binds
    abstract fun bindLoadAddressesUseCase(
        impl: LoadAddressesUseCaseImpl
    ): LoadAddressesUseCase

    @Binds
    abstract fun bindSaveAddressUseCase(
        impl: SaveAddressUseCaseImpl
    ): SaveAddressUseCase

    @Binds
    abstract fun bindDeleteAddressUseCase(
        impl: DeleteAddressUseCaseImpl
    ): DeleteAddressUseCase

    @Binds
    abstract fun bindSetDefaultAddressUseCase(
        impl: SetDefaultAddressUseCaseImpl
    ): SetDefaultAddressUseCase

    @Binds
    abstract fun bindGetUserWalletUseCase(
        impl: GetUserWalletUseCaseImpl
    ): GetUserWalletUseCase

    @Binds
    abstract fun bindGetWalletConfigUseCase(
        impl: GetWalletConfigUseCaseImpl
    ): GetWalletConfigUseCase

    @Binds
    abstract fun bindGetAllStoryViewUseCase(
        impl: GetAllStoryViewUseCaseImpl
    ): GetAllStoryViewUseCase

    @Binds
    abstract fun bindAddStoryViewUseCase(
        impl: AddStoryViewUseCaseImpl
    ): AddStoryViewUseCase

    @Binds
    abstract fun bindEditUserDetailsUseCase(
        impl: EditUserDetailsUseCaseImpl
    ): EditUserDetailsUseCase

    @Binds
    abstract fun bindSetLanguageUseCase(
        impl: SetLanguageUseCaseImpl
    ): SetLanguageUseCase

    @Binds
    abstract fun bindObserveLanguageUseCase(
        impl: ObserveLanguageUseCaseImpl
    ): ObserveLanguageUseCase
}
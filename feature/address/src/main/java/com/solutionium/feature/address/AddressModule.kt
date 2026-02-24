package com.solutionium.feature.address

import com.solutionium.shared.domain.user.getUserDomainModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun getAddressModules() = setOf(addressModule) + getUserDomainModules()

val addressModule = module {
    viewModel {
        AddressViewModel(
            saveAddressUseCase = get(),
            loadAddressUseCase = get(),
            deleteAddressUseCase = get(),
            setAsDefaultAddressUseCase = get(),
            savedStateHandle = get()
        )
    }
}

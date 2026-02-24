package com.solutionium.domain.user

import com.solutionium.shared.data.user.WooUserRepository

interface SetDefaultAddressUseCase {

    suspend operator fun invoke(addressId: Int)

}

internal class SetDefaultAddressUseCaseImpl(

    private val userRepository: WooUserRepository

): SetDefaultAddressUseCase {
    override suspend fun invoke(addressId: Int) {
        userRepository.setDefaultAddress(addressId)
    }
}
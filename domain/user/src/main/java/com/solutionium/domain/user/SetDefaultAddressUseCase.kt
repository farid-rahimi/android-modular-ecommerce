package com.solutionium.domain.user

import com.solutionium.data.woo.user.WooUserRepository
import javax.inject.Inject

interface SetDefaultAddressUseCase {

    suspend operator fun invoke(addressId: Int)

}

internal class SetDefaultAddressUseCaseImpl @Inject constructor(

    private val userRepository: WooUserRepository

): SetDefaultAddressUseCase {
    override suspend fun invoke(addressId: Int) {
        userRepository.setDefaultAddress(addressId)
    }
}
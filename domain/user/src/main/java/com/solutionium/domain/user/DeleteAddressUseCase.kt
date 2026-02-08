package com.solutionium.domain.user

import com.solutionium.data.model.Address
import com.solutionium.data.woo.user.WooUserRepository
import javax.inject.Inject

interface DeleteAddressUseCase {

    suspend operator fun invoke(address: Address)

}

internal class DeleteAddressUseCaseImpl @Inject constructor(

    private val userRepository: WooUserRepository

): DeleteAddressUseCase {
    override suspend fun invoke(address: Address) {
        userRepository.deleteAddress(address)
    }
}
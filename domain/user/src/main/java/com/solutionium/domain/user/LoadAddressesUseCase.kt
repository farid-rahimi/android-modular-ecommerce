package com.solutionium.domain.user

import com.solutionium.shared.data.model.Address
import com.solutionium.data.woo.user.WooUserRepository
import kotlinx.coroutines.flow.Flow

interface LoadAddressesUseCase {
    operator fun invoke(): Flow<List<Address>>
    operator fun invoke(addressId: Int): Flow<Address?>
}

internal class LoadAddressesUseCaseImpl(
    private val userRepository: WooUserRepository
) : LoadAddressesUseCase {
    override fun invoke(): Flow<List<Address>> {
        return userRepository.getAddresses()
    }

    override fun invoke(addressId: Int): Flow<Address?> {
        return userRepository.getAddressById(addressId)
    }
}
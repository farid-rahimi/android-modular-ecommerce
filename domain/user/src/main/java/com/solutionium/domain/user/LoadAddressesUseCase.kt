package com.solutionium.domain.user

import com.solutionium.data.model.Address
import com.solutionium.data.woo.user.WooUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LoadAddressesUseCase {
    operator fun invoke(): Flow<List<Address>>
    operator fun invoke(addressId: Int): Flow<Address?>
}

internal class LoadAddressesUseCaseImpl @Inject constructor(
    private val userRepository: WooUserRepository
) : LoadAddressesUseCase {
    override fun invoke(): Flow<List<Address>> {
        return userRepository.getAddresses()
    }

    override fun invoke(addressId: Int): Flow<Address?> {
        return userRepository.getAddressById(addressId)
    }
}
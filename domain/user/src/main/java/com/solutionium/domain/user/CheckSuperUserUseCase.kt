package com.solutionium.domain.user

import com.solutionium.shared.data.user.WooUserRepository
import kotlinx.coroutines.flow.Flow

interface CheckSuperUserUseCase {
    operator fun invoke(): Flow<Boolean>
}

internal class CheckSuperUserUseCaseImpl(

    private val userRepository: WooUserRepository

) : CheckSuperUserUseCase {
    override fun invoke(): Flow<Boolean> =
        userRepository.isSuperUser()


}
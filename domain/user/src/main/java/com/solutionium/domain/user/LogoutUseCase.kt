package com.solutionium.domain.user

import com.solutionium.data.woo.user.WooUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface LogoutUseCase {
    suspend operator fun invoke(): Flow<Boolean>
}

internal class LogoutUseCaseImpl(
    private val userRepository: WooUserRepository
) : LogoutUseCase {
    override suspend fun invoke(): Flow<Boolean> = flow {
        emit(userRepository.logout())
    }
}
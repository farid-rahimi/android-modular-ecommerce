package com.solutionium.domain.user

import com.solutionium.data.woo.user.WooUserRepository
import kotlinx.coroutines.flow.Flow

interface CheckLoginUserUseCase {
    operator fun invoke(): Flow<Boolean>
}

internal class CheckLoginUserUseCaseImpl(

    private val userRepository: WooUserRepository

) : CheckLoginUserUseCase {
    override fun invoke(): Flow<Boolean> =
        userRepository.isLoggedIn()


}
package com.solutionium.domain.user

import com.solutionium.data.woo.user.WooUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface CheckLoginUserUseCase {
    operator fun invoke(): Flow<Boolean>
}

internal class CheckLoginUserUseCaseImpl @Inject constructor(

    private val userRepository: WooUserRepository

) : CheckLoginUserUseCase {
    override fun invoke(): Flow<Boolean> =
        userRepository.isLoggedIn()


}
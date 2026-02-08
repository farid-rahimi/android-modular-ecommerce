package com.solutionium.domain.user

import com.solutionium.data.woo.user.WooUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface CheckSuperUserUseCase {
    operator fun invoke(): Flow<Boolean>
}

internal class CheckSuperUserUseCaseImpl @Inject constructor(

    private val userRepository: WooUserRepository

) : CheckSuperUserUseCase {
    override fun invoke(): Flow<Boolean> =
        userRepository.isSuperUser()


}
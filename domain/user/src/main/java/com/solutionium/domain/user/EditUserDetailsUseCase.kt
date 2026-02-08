package com.solutionium.domain.user

import com.solutionium.data.model.GeneralError
import com.solutionium.data.model.Result
import com.solutionium.data.model.UserDetails
import com.solutionium.data.woo.user.WooUserRepository
import javax.inject.Inject

interface EditUserDetailsUseCase {

    suspend operator fun invoke(userDetails: UserDetails): Result<UserDetails, GeneralError>

}

class EditUserDetailsUseCaseImpl @Inject constructor(
    private val userRepository: WooUserRepository
): EditUserDetailsUseCase {
    override suspend fun invoke(userDetails: UserDetails): Result<UserDetails, GeneralError> =
        userRepository.updateUserProfile(userDetails)

}
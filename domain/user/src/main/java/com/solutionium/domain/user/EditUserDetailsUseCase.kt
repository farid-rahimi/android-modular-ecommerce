package com.solutionium.domain.user

import com.solutionium.shared.data.model.GeneralError
import com.solutionium.shared.data.model.Result
import com.solutionium.shared.data.model.UserDetails
import com.solutionium.shared.data.user.WooUserRepository

interface EditUserDetailsUseCase {

    suspend operator fun invoke(userDetails: UserDetails): Result<UserDetails, GeneralError>

}

class EditUserDetailsUseCaseImpl(
    private val userRepository: WooUserRepository
): EditUserDetailsUseCase {
    override suspend fun invoke(userDetails: UserDetails): Result<UserDetails, GeneralError> =
        userRepository.updateUserProfile(userDetails)

}
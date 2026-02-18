package com.solutionium.domain.user

import com.solutionium.data.woo.user.WooUserRepository
import kotlinx.coroutines.flow.Flow

interface SetLanguageUseCase {
    suspend operator fun invoke(languageCode: String)
}

interface ObserveLanguageUseCase {
    operator fun invoke(): Flow<String?>
}

class SetLanguageUseCaseImpl(
    private val userRepository: WooUserRepository
) : SetLanguageUseCase {
    override suspend fun invoke(languageCode: String) {
        userRepository.setLanguage(languageCode)
    }

}

class ObserveLanguageUseCaseImpl(
    private val userRepository: WooUserRepository
) : ObserveLanguageUseCase {
    override fun invoke(): Flow<String?> {
        return userRepository.getLanguage()
    }
}
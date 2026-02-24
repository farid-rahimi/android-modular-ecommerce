package com.solutionium.shared.domain.config

import com.solutionium.shared.data.model.ContactInfo

interface GetContactInfoUseCase {

    suspend operator fun invoke(): ContactInfo?

}
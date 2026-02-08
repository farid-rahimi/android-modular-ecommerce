package com.solutionium.domain.config

import com.solutionium.data.model.ContactInfo

interface GetContactInfoUseCase {

    suspend operator fun invoke(): ContactInfo?

}
package com.solutionium.domain.config

import com.solutionium.shared.data.model.BACSDetails

interface GetBACSDetailsUseCase {

    suspend operator fun invoke(): BACSDetails?

}
package com.solutionium.domain.config

import com.solutionium.data.model.BACSDetails

interface GetBACSDetailsUseCase {

    suspend operator fun invoke(): BACSDetails?

}
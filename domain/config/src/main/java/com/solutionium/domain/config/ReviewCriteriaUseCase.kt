package com.solutionium.domain.config

interface ReviewCriteriaUseCase {

    suspend operator fun invoke(catIds: List<Int>): List<String>

}
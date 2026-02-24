package com.solutionium.shared.domain.config

interface GetAppImages {

    suspend operator fun invoke(): Map<Int, String>

}
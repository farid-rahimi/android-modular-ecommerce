package com.solutionium.domain.config

interface GetAppImages {

    suspend operator fun invoke(): Map<Int, String>

}
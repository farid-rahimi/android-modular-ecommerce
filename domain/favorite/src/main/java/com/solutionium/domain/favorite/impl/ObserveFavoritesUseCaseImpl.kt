package com.solutionium.domain.favorite.impl

import com.solutionium.data.favorite.FavoriteRepository
import com.solutionium.data.woo.user.WooUserRepository
import com.solutionium.domain.favorite.ObserveFavoritesUseCase
import com.solutionium.domain.user.CheckLoginUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ObserveFavoritesUseCaseImpl @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val userRepository: WooUserRepository,
    //private val checkLoginUserUseCase: CheckLoginUserUseCase
) : ObserveFavoritesUseCase {
    override fun invoke(): Flow<List<Int>>  =
        favoriteRepository.observeFavoriteIds()


         //Use flatMapLatest to react to login changes
//        return userRepository.isLoggedIn() // This should return a Flow<Boolean>
//            .flatMapLatest { isLoggedIn ->
//                if (isLoggedIn) {
//                    //.flatMapLatest { userId ->
//                        // Pass the freshly fetched userId down to the repository.
//                     //   favoriteRepository.observeFavoriteIds(userId)
//                    //}
//                    // If logged in, return the flow of user's favorites
//                    favoriteRepository.observeFavoriteIds(userRepository.getCurrentUserId())
//                } else {
//                    // If not logged in, return a flow with an empty set
//                    flowOf(emptyList())
//                }
//            }


    override suspend fun getSnapshot(): Set<Int> =
        favoriteRepository.getFavoriteIds()
}
package com.solutionium.data.favorite

import com.solutionium.data.database.entity.FavoriteEntity
import com.solutionium.shared.data.model.Favorite

fun FavoriteEntity.toModel() = Favorite(
    productId = productId
)

fun Favorite.toEntity() = FavoriteEntity(
    productId = productId,
)
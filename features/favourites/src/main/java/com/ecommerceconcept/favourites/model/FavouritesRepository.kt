package com.ecommerceconcept.favourites.model

import kotlinx.coroutines.flow.StateFlow

interface FavouritesRepository {
    fun favouritesFlow(): StateFlow<List<Int>>
    fun toggleFavourite(productId: Int)
    fun isFavourite(productId: Int): Boolean
    fun clearFavourites()
}
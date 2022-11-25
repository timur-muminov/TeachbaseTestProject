package com.ecommerceconcept.app_dependencies.repository

import com.ecommerceconcept.favourites.model.FavouritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FavouritesRepositoryImpl : FavouritesRepository {

    private val favouritesStorageMutableStateFlow = MutableStateFlow<List<Int>>(emptyList())
    override fun favouritesFlow() = favouritesStorageMutableStateFlow.asStateFlow()

    override fun toggleFavourite(productId: Int) =
        if (favouritesFlow().value.contains(productId)) updateStorage { it.remove(productId) } else updateStorage { it.add(productId) }

    private fun updateStorage(action: (MutableList<Int>) -> Unit) {
        favouritesStorageMutableStateFlow.update { list ->
            list.toMutableList().also { action(it) }
        }
    }

    override fun isFavourite(productId: Int): Boolean = favouritesStorageMutableStateFlow.value.contains(productId)
    override fun clearFavourites() {
        favouritesStorageMutableStateFlow.value = emptyList()
    }
}
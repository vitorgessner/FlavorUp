package com.example.flavorup.ui.screens.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavorup.data.model.FavoriteRecipe
import com.example.flavorup.data.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FavoritesUiState(
    val favorites: List<FavoriteRecipe> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            favoritesRepository.getFavoritesFlow()
                .collect { favorites ->
                    _uiState.value = _uiState.value.copy(
                        favorites = favorites,
                        isLoading = false
                    )
                }
        }
    }

    fun removeFavorite(recipeId: Int) {
        viewModelScope.launch {
            try {
                favoritesRepository.removeFavorite(recipeId)
            } catch (e: Exception) {
                Log.e("FavoritesViewModel", "Erro ao remover favorito", e)
                _uiState.value = _uiState.value.copy(
                    error = "Erro ao remover: ${e.message}"
                )
            }
        }
    }
}
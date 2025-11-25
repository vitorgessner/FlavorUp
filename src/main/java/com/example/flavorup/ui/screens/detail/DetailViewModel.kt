package com.example.flavorup.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavorup.data.model.RecipeDetail
import com.example.flavorup.data.repository.FavoritesRepository
import com.example.flavorup.data.repository.RecipeRepository
import com.example.flavorup.data.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailUiState(
    val recipe: RecipeDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isFavorite: Boolean = false,
    val favoriteActionInProgress: Boolean = false
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private val favoritesRepository: FavoritesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val recipeId: Int = savedStateHandle.get<String>("recipeId")?.toIntOrNull() ?: 0

    init {
        loadRecipeDetails()
        checkIfFavorite()
    }

    private fun loadRecipeDetails() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            when (val result = repository.getRecipeDetails(recipeId)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        recipe = result.data,
                        isLoading = false
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }
                is Result.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }

    private fun checkIfFavorite() {
        viewModelScope.launch {
            val isFav = favoritesRepository.isFavorite(recipeId)
            _uiState.value = _uiState.value.copy(isFavorite = isFav)
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(favoriteActionInProgress = true)

            val recipe = _uiState.value.recipe ?: return@launch

            val result = if (_uiState.value.isFavorite) {
                favoritesRepository.removeFavorite(recipeId)
            } else {
                val basicRecipe = com.example.flavorup.data.model.Recipe(
                    id = recipe.id,
                    title = recipe.title,
                    image = recipe.image,
                    usedIngredientCount = 0,
                    missedIngredientCount = 0,
                    likes = 0
                )
                favoritesRepository.addFavorite(basicRecipe)
            }

            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(
                    isFavorite = !_uiState.value.isFavorite,
                    favoriteActionInProgress = false
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    error = result.exceptionOrNull()?.message ?: "Erro ao favoritar",
                    favoriteActionInProgress = false
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun retry() {
        _uiState.value.recipe?.id?.let { loadRecipeDetails() }
    }
}
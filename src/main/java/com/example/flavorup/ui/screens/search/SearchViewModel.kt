package com.example.flavorup.ui.screens.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavorup.data.model.Recipe
import com.example.flavorup.data.repository.RecipeRepository
import com.example.flavorup.data.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val recipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = ""
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: RecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        val ingredientsParam = savedStateHandle.get<String>("ingredients") ?: ""
        val ingredients = ingredientsParam.split(",").filter { it.isNotBlank() }
        if (ingredients.isNotEmpty()) {
            searchRecipes(ingredients)
        }
    }

    private fun searchRecipes(ingredients: List<String>) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                searchQuery = ingredients.joinToString(", ")
            )

            when (val result = repository.getRecipesByIngredients(ingredients)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        recipes = result.data,
                        isLoading = false
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = result.message,
                        isLoading = false
                    )
                }
                is Result.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }

    fun retry() {
        val ingredients = _uiState.value.searchQuery.split(",").map { it.trim() }
        searchRecipes(ingredients)
    }
}
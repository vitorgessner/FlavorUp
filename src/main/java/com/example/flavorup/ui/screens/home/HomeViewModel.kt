package com.example.flavorup.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavorup.data.model.DietPreference
import com.example.flavorup.data.model.RecipeFilters
import com.example.flavorup.data.model.SkillLevel
import com.example.flavorup.data.repository.RecipeRepository
import com.example.flavorup.data.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val filters: RecipeFilters = RecipeFilters(),
    val currentIngredient: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun updateCurrentIngredient(ingredient: String) {
        _uiState.value = _uiState.value.copy(currentIngredient = ingredient)
    }

    fun addIngredient() {
        val ingredientsText = _uiState.value.currentIngredient.trim()
        if (ingredientsText.isNotEmpty()) {
            // Divide por vírgula e adiciona cada ingrediente separadamente
            val newIngredients = ingredientsText
                .split(",")
                .map { it.trim() }
                .filter { it.isNotEmpty() }

            val updatedIngredients = _uiState.value.filters.ingredients + newIngredients
            _uiState.value = _uiState.value.copy(
                filters = _uiState.value.filters.copy(ingredients = updatedIngredients),
                currentIngredient = ""
            )
        }
    }

    fun removeIngredient(ingredient: String) {
        val updatedIngredients = _uiState.value.filters.ingredients.filter { it != ingredient }
        _uiState.value = _uiState.value.copy(
            filters = _uiState.value.filters.copy(ingredients = updatedIngredients)
        )
    }

    fun updateSkillLevel(level: SkillLevel) {
        _uiState.value = _uiState.value.copy(
            filters = _uiState.value.filters.copy(skillLevel = level)
        )
    }

    fun updateTimeAvailable(time: Int) {
        _uiState.value = _uiState.value.copy(
            filters = _uiState.value.filters.copy(timeAvailable = time)
        )
    }

    fun toggleDietPreference(preference: DietPreference) {
        val currentPreferences = _uiState.value.filters.dietPreferences
        val updatedPreferences = if (currentPreferences.contains(preference)) {
            currentPreferences - preference
        } else {
            currentPreferences + preference
        }
        _uiState.value = _uiState.value.copy(
            filters = _uiState.value.filters.copy(dietPreferences = updatedPreferences)
        )
    }
}
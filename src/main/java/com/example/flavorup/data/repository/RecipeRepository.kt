package com.example.flavorup.data.repository

import com.example.flavorup.data.model.Recipe
import com.example.flavorup.data.model.RecipeDetail
import com.example.flavorup.data.remote.RecipeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

@Singleton
class RecipeRepository @Inject constructor(
    private val api: RecipeApi
) {
    suspend fun getRecipesByIngredients(ingredients: List<String>): Result<List<Recipe>> {
        return withContext(Dispatchers.IO) {
            try {
                val query = ingredients.joinToString(",")
                val recipes = api.searchRecipesByIngredients(query)
                Result.Success(recipes)
            } catch (e: Exception) {
                Result.Error(e.message ?: "Erro desconhecido ao buscar receitas")
            }
        }
    }

    suspend fun getRecipeDetails(id: Int): Result<RecipeDetail> {
        return withContext(Dispatchers.IO) {
            try {
                val details = api.getRecipeDetails(id)
                Result.Success(details)
            } catch (e: Exception) {
                Result.Error(e.message ?: "Erro ao buscar detalhes da receita")
            }
        }
    }
}
package com.example.flavorup.data.remote

import com.example.flavorup.data.model.Recipe
import com.example.flavorup.data.model.RecipeDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {
    @GET("recipes/findByIngredients")
    suspend fun searchRecipesByIngredients(
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int = 20,
        @Query("ranking") ranking: Int = 2,
        @Query("ignorePantry") ignorePantry: Boolean = true,
        @Query("apiKey") apiKey: String = ApiConstants.API_KEY
    ): List<Recipe>

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetails(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = ApiConstants.API_KEY
    ): RecipeDetail
}

// Constantes
object ApiConstants {
    const val BASE_URL = "https://api.spoonacular.com/"
    const val API_KEY = "8a9d836f6851434386ac90e2b1ec40e9"
}
package com.example.flavorup.data.model

import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("usedIngredientCount")
    val usedIngredientCount: Int = 0,

    @SerializedName("missedIngredientCount")
    val missedIngredientCount: Int = 0,

    @SerializedName("likes")
    val likes: Int = 0
)

// Modelo para detalhes completos da receita
data class RecipeDetail(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("servings")
    val servings: Int = 0,

    @SerializedName("readyInMinutes")
    val readyInMinutes: Int = 0,

    @SerializedName("extendedIngredients")
    val ingredients: List<Ingredient> = emptyList(),

    @SerializedName("instructions")
    val instructions: String? = null,

    @SerializedName("summary")
    val summary: String? = null
)

data class Ingredient(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("original")
    val original: String
)

// Classes para os filtros da Home
data class RecipeFilters(
    val ingredients: List<String> = emptyList(),
    val skillLevel: SkillLevel = SkillLevel.BEGINNER,
    val timeAvailable: Int = 30,
    val dietPreferences: List<DietPreference> = emptyList()
)

enum class SkillLevel {
    BEGINNER,
    MEDIUM,
    ADVANCED
}

enum class DietPreference {
    VEGETARIAN,
    VEGAN,
    GLUTEN_FREE
}
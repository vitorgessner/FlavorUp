package com.example.flavorup.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class FavoriteRecipe(
    @DocumentId
    val id: String = "",

    val recipeId: Int = 0,
    val title: String = "",
    val image: String? = null,
    val usedIngredientCount: Int = 0,
    val missedIngredientCount: Int = 0,
    val likes: Int = 0,

    @ServerTimestamp
    val favoritedAt: Date? = null,

    val userId: String = ""
) {
    // Construtor vazio necessário para o Firebase
    constructor() : this("", 0, "", null, 0, 0, 0, null, "")

    companion object {
        fun fromRecipe(recipe: Recipe, userId: String): FavoriteRecipe {
            return FavoriteRecipe(
                recipeId = recipe.id,
                title = recipe.title,
                image = recipe.image,
                usedIngredientCount = recipe.usedIngredientCount,
                missedIngredientCount = recipe.missedIngredientCount,
                likes = recipe.likes,
                userId = userId
            )
        }
    }

    fun toRecipe(): Recipe {
        return Recipe(
            id = recipeId,
            title = title,
            image = image,
            usedIngredientCount = usedIngredientCount,
            missedIngredientCount = missedIngredientCount,
            likes = likes
        )
    }
}
package com.example.flavorup.data.repository

import android.util.Log
import com.example.flavorup.data.firebase.FirebaseAuthService
import com.example.flavorup.data.model.FavoriteRecipe
import com.example.flavorup.data.model.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authService: FirebaseAuthService
) {
    private val favoritesCollection = firestore.collection("favorites")

    suspend fun addFavorite(recipe: Recipe): kotlin.Result<Unit> {
        return try {
            Log.d("FavoritesRepository", "Adicionando favorito: ${recipe.id}")
            val userId = authService.ensureUserLoggedIn()
            val favorite = FavoriteRecipe.fromRecipe(recipe, userId)

            favoritesCollection
                .document("${userId}_${recipe.id}")
                .set(favorite)
                .await()

            Log.d("FavoritesRepository", "Favorito adicionado com sucesso")
            kotlin.Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FavoritesRepository", "Erro ao adicionar favorito", e)
            kotlin.Result.failure(e)
        }
    }

    suspend fun removeFavorite(recipeId: Int): kotlin.Result<Unit> {
        return try {
            Log.d("FavoritesRepository", "Removendo favorito: $recipeId")
            val userId = authService.ensureUserLoggedIn()

            favoritesCollection
                .document("${userId}_${recipeId}")
                .delete()
                .await()

            Log.d("FavoritesRepository", "Favorito removido com sucesso")
            kotlin.Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FavoritesRepository", "Erro ao remover favorito", e)
            kotlin.Result.failure(e)
        }
    }

    suspend fun isFavorite(recipeId: Int): Boolean {
        return try {
            val userId = authService.currentUserId
            if (userId == null) {
                Log.d("FavoritesRepository", "Usuário não logado, retornando false")
                return false
            }

            val doc = favoritesCollection
                .document("${userId}_${recipeId}")
                .get()
                .await()

            val exists = doc.exists()
            Log.d("FavoritesRepository", "Receita $recipeId é favorita: $exists")
            exists
        } catch (e: Exception) {
            Log.e("FavoritesRepository", "Erro ao verificar favorito", e)
            false
        }
    }

    fun getFavoritesFlow(): Flow<List<FavoriteRecipe>> = callbackFlow {
        Log.d("FavoritesRepository", "Iniciando getFavoritesFlow")

        val userId = authService.currentUserId

        if (userId == null) {
            Log.d("FavoritesRepository", "Usuário não logado, retornando lista vazia")
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        Log.d("FavoritesRepository", "Usuário logado: $userId, configurando listener")

        val listener = favoritesCollection
            .whereEqualTo("userId", userId)
            .orderBy("favoritedAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("FavoritesRepository", "Erro no snapshot listener", error)
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot == null) {
                    Log.w("FavoritesRepository", "Snapshot é null")
                    trySend(emptyList())
                    return@addSnapshotListener
                }

                Log.d("FavoritesRepository", "Recebidos ${snapshot.documents.size} documentos")

                val favorites = snapshot.documents.mapNotNull { doc ->
                    try {
                        doc.toObject(FavoriteRecipe::class.java)
                    } catch (e: Exception) {
                        Log.e("FavoritesRepository", "Erro ao converter documento ${doc.id}", e)
                        null
                    }
                }

                Log.d("FavoritesRepository", "Convertidos ${favorites.size} favoritos")
                trySend(favorites)
            }

        awaitClose {
            Log.d("FavoritesRepository", "Removendo listener")
            listener.remove()
        }
    }

    suspend fun getAllFavorites(): kotlin.Result<List<FavoriteRecipe>> {
        return try {
            val userId = authService.currentUserId

            if (userId == null) {
                Log.d("FavoritesRepository", "getAllFavorites: usuário não logado")
                return kotlin.Result.success(emptyList())
            }

            val snapshot = favoritesCollection
                .whereEqualTo("userId", userId)
                .orderBy("favoritedAt", Query.Direction.DESCENDING)
                .get()
                .await()

            val favorites = snapshot.documents.mapNotNull { doc ->
                doc.toObject(FavoriteRecipe::class.java)
            }

            Log.d("FavoritesRepository", "getAllFavorites retornou ${favorites.size} itens")
            kotlin.Result.success(favorites)
        } catch (e: Exception) {
            Log.e("FavoritesRepository", "Erro em getAllFavorites", e)
            kotlin.Result.failure(e)
        }
    }
}
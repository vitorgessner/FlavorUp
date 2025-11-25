package com.example.flavorup.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthService @Inject constructor(
    private val auth: FirebaseAuth
) {
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    val currentUserId: String?
        get() = auth.currentUser?.uid

    val isUserLoggedIn: Boolean
        get() = auth.currentUser != null

    suspend fun signInAnonymously(): Result<FirebaseUser> {
        return try {
            val result = auth.signInAnonymously().await()
            result.user?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Falha ao fazer login anônimo"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun ensureUserLoggedIn(): String {
        return currentUserId ?: run {
            signInAnonymously().getOrThrow()
            currentUserId ?: throw Exception("Não foi possível obter ID do usuário")
        }
    }

    fun signOut() {
        auth.signOut()
    }
}
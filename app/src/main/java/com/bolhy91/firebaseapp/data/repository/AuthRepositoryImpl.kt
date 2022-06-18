package com.bolhy91.firebaseapp.data.repository

import com.bolhy91.firebaseapp.domain.repository.AuthRepository
import com.bolhy91.firebaseapp.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun login(email: String, password: String): Flow<Resource<FirebaseUser?>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
                val user = firebaseAuth.currentUser
                emit(Resource.Success(user))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: e.toString()))
            }
        }
    }
}
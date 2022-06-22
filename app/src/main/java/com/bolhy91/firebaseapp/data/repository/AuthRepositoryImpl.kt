package com.bolhy91.firebaseapp.data.repository

import com.bolhy91.firebaseapp.domain.repository.AuthRepository
import com.bolhy91.firebaseapp.utils.Resource
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun login(email: String, password: String): Flow<Resource<Boolean>> {
        return flow {
            try {
                emit(Resource.Loading(true))
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
                val user = firebaseAuth.currentUser
                emit(Resource.Success(user != null))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: e.toString()))
            } catch (e: FirebaseAuthException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: e.toString()))
            } catch (e: FirebaseException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: e.toString()))
            }
        }
    }

    override suspend fun register(email: String, password: String): Flow<Resource<Boolean>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                val user = firebaseAuth.currentUser
                emit(Resource.Success(user != null))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: e.toString()))
            } catch (e: FirebaseAuthException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: e.toString()))

            } catch (e: FirebaseException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: e.toString()))
            }
        }
    }

    override suspend fun logout(): Flow<Resource<Boolean>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))
                firebaseAuth.currentUser?.delete()?.await()
                emit(Resource.Success(true))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: e.toString()))
            } catch (e: FirebaseAuthException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: e.toString()))

            } catch (e: FirebaseException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: e.toString()))
            }
        }
    }
}
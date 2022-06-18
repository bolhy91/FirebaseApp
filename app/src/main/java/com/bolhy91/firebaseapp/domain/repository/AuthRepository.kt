package com.bolhy91.firebaseapp.domain.repository

import com.bolhy91.firebaseapp.utils.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<Resource<FirebaseUser?>>
}
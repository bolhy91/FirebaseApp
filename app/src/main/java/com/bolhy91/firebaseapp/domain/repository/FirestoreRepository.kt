package com.bolhy91.firebaseapp.domain.repository

import com.bolhy91.firebaseapp.domain.model.Task
import com.bolhy91.firebaseapp.utils.Response
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {
    suspend fun addTask(
        title: String,
        description: String,
        isCheck: Boolean = false
    ): Flow<Response<Void?>>

    suspend fun getTask(): Flow<Response<List<Task>>>
    suspend fun removeTask(taskId: String): Flow<Response<Void?>>
}
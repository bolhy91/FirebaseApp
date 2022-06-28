package com.bolhy91.firebaseapp.domain.repository

import com.bolhy91.firebaseapp.domain.model.Task
import com.bolhy91.firebaseapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {
    suspend fun addTask(
        title: String,
        description: String,
        isCheck: Boolean = false
    ): Flow<Resource<Void?>>

    suspend fun getTask(): Flow<Resource<List<Task>>>
    suspend fun removeTask(taskId: String): Flow<Resource<Void?>>
}
package com.bolhy91.firebaseapp.data.repository

import android.util.Log
import com.bolhy91.firebaseapp.domain.model.Task
import com.bolhy91.firebaseapp.domain.repository.FirestoreRepository
import com.bolhy91.firebaseapp.utils.Resource
import com.bolhy91.firebaseapp.utils.Response
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepositoryImpl @Inject
constructor(
    private val taskRef: CollectionReference
) : FirestoreRepository {
    override suspend fun addTask(
        title: String,
        description: String,
        isCheck: Boolean
    ): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            val id = taskRef.document().id
            val task = Task(
                title = title,
                description = description,
                isCheck = isCheck
            )
            val result = taskRef.document(id).set(task).await()
            emit(Response.Success(data = result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response.Error(e.message ?: e.toString()))
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            emit(Response.Error(e.message ?: e.toString()))
        }
    }

    override suspend fun getTask(): Flow<Response<List<Task>>> {
        return flow {
            emit(Response.Loading)
            val tasks: List<Task>
            try {
                tasks = taskRef.get().await().documents.mapNotNull { snapshot ->
                    snapshot.toObject(Task::class.java)
                }
                emit(Response.Success(data = tasks))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Response.Error(e.message ?: e.toString()))
            } catch (e: FirebaseFirestoreException) {
                e.printStackTrace()
                emit(Response.Error(e.message ?: e.toString()))
            }
        }
    }

    override suspend fun removeTask(taskId: String): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            val delete = taskRef.document(taskId).delete().await()
            Log.i("DELETE TASK: ", delete.toString())
            emit(Response.Success(delete))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response.Error(e.message ?: e.toString()))
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            emit(Response.Error(e.message ?: e.toString()))
        }
    }
}
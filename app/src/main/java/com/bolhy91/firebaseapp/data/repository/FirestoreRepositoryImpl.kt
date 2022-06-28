package com.bolhy91.firebaseapp.data.repository

import android.util.Log
import com.bolhy91.firebaseapp.domain.model.Task
import com.bolhy91.firebaseapp.domain.repository.FirestoreRepository
import com.bolhy91.firebaseapp.utils.Resource
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
    ): Flow<Resource<Void?>> {
        return flow {
            try {
                emit(Resource.Loading(true))
                val task = Task(
                    title = title,
                    description = description,
                    isCheck = isCheck
                )
                val result = taskRef.document().set(task).await()
                Log.i("RESULT ADD: ", result.toString())
                emit(Resource.Success(data = result))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: e.toString()))
            } catch (e: FirebaseFirestoreException) {
                emit(Resource.Error(e.message ?: e.toString()))
            }
        }
    }

    override suspend fun getTask(): Flow<Resource<List<Task>>> {
        return flow {
            emit(Resource.Loading(true))
            val tasks: List<Task>
            try {
                tasks = taskRef.get().await().documents.mapNotNull { snapshot ->
                    snapshot.toObject(Task::class.java)
                }
                emit(Resource.Success(data = tasks))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: e.toString()))
            } catch (e: FirebaseFirestoreException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: e.toString()))
            }
        }
    }

    override suspend fun removeTask(taskId: String): Flow<Resource<Void?>> = flow {
        try {
            emit(Resource.Loading(true))
            val delete = taskRef.document(taskId).delete().await()
            Log.i("DELETE TASK: ", delete.toString())
            emit(Resource.Success(delete))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: e.toString()))
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            emit(Resource.Error(e.message ?: e.toString()))
        }
    }
}
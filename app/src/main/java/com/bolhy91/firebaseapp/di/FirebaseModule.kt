package com.bolhy91.firebaseapp.di

import com.bolhy91.firebaseapp.data.repository.AuthRepositoryImpl
import com.bolhy91.firebaseapp.data.repository.FirestoreRepositoryImpl
import com.bolhy91.firebaseapp.domain.repository.AuthRepository
import com.bolhy91.firebaseapp.domain.repository.FirestoreRepository
import com.bolhy91.firebaseapp.utils.Constants.NAME_PROPERTY
import com.bolhy91.firebaseapp.utils.Constants.TASK_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(auth)
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideTaskRef(db: FirebaseFirestore) = db.collection(TASK_COLLECTION)

    @Provides
    @Singleton
    fun provideFirestoreRepository(taskRef: CollectionReference): FirestoreRepository {
        return FirestoreRepositoryImpl(taskRef)
    }
}
package com.example.myfirebasechat.di

import com.example.myfirebasechat.model.repository.ChatRepository
import com.example.myfirebasechat.model.repository.ChatRepositoryImpl
import com.example.myfirebasechat.model.repository.SignUpRepository
import com.example.myfirebasechat.model.repository.SignUpRepositoryImpl
import com.example.myfirebasechat.model.repository.UsersRepository
import com.example.myfirebasechat.model.repository.UsersRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideDatabaseReference(): DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("Users")
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()

    }

    @Provides
    fun provideChatRepository(databaseReference: DatabaseReference): ChatRepository {
        return ChatRepositoryImpl(databaseReference = databaseReference)
    }

    @Provides
    fun provideSignUpRepository(
        databaseReference: DatabaseReference,
        auth: FirebaseAuth
    ): SignUpRepository {
        return SignUpRepositoryImpl(databaseReference = databaseReference, auth = auth)

    }

    @Provides
    fun provideUsersRepository(
        databaseReference: DatabaseReference,
        auth: FirebaseAuth
    ): UsersRepository {
        return UsersRepositoryImpl(databaseReference = databaseReference, auth = auth)
    }

}

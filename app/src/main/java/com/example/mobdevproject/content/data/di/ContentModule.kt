package com.example.mobdevproject.content.data.di

import com.example.mobdevproject.content.data.interfaces.FirestoreAddInterface
import com.example.mobdevproject.content.data.repositories.FirestoreAddRepository
import com.example.mobdevproject.content.data.interfaces.FirestoreReadInterface
import com.example.mobdevproject.content.data.interfaces.StorageInterface
import com.example.mobdevproject.content.data.repositories.FirestoreReadRepository
import com.example.mobdevproject.content.data.repositories.StorageRepository
import com.example.mobdevproject.content.domain.FirestoreAddUseCase
import com.example.mobdevproject.content.domain.FirestoreReadUseCase
import com.example.mobdevproject.content.domain.interfaces.FirestoreAddUseCaseInterface
import com.example.mobdevproject.content.domain.interfaces.FirestoreReadUseCaseInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ContentModule {

    @Singleton
    @Binds
    fun getFirestoreAddInterface(impl: FirestoreAddRepository): FirestoreAddInterface

    @Singleton
    @Binds
    fun getFirestoreReadInterface(impl: FirestoreReadRepository): FirestoreReadInterface

    @Singleton
    @Binds
    fun getFirestoreAddUseCaseInterface(impl: FirestoreAddUseCase): FirestoreAddUseCaseInterface

    @Singleton
    @Binds
    fun getFirestoreReadUseCaseInterface(impl: FirestoreReadUseCase): FirestoreReadUseCaseInterface

    @Singleton
    @Binds
    fun getStorageInterface(impl: StorageRepository): StorageInterface

}
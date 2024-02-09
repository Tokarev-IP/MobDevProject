package com.example.mobdevproject.login.data.di

import com.example.mobdevproject.login.data.AuthRepository
import com.example.mobdevproject.login.data.interfaces.AuthRepositoryInterface
import com.example.mobdevproject.login.domain.AuthUseCase
import com.example.mobdevproject.login.domain.EmailAuthUseCase
import com.example.mobdevproject.login.domain.PhoneAuthProcessing
import com.example.mobdevproject.login.domain.interfaces.AuthUseCaseInterface
import com.example.mobdevproject.login.domain.interfaces.EmailAuthUseCaseInterface
import com.example.mobdevproject.login.domain.interfaces.PhoneAuthProcessingInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LoginModules {

    @Singleton
    @Binds
    fun getAuthRepositoryInterface(impl: AuthRepository): AuthRepositoryInterface

    @Singleton
    @Binds
    fun getAuthUseCaseInterface(impl: AuthUseCase): AuthUseCaseInterface

    @Singleton
    @Binds
    fun getEmailAuthUseCaseInterface(impl: EmailAuthUseCase): EmailAuthUseCaseInterface

    @Singleton
    @Binds
    fun getPhoneAuthProcessingInterface(impl: PhoneAuthProcessing): PhoneAuthProcessingInterface
}
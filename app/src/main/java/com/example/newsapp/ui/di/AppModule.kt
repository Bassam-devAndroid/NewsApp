package com.example.newsapp.ui.di

import android.app.Application
import com.example.newsapp.ui.data.LocalUserManagerImpl
import com.example.newsapp.ui.domain.manager.LocalUserManager
import com.example.newsapp.ui.domain.usecases.AppEntryUseCases
import com.example.newsapp.ui.domain.usecases.LoadAppEntryUseCase
import com.example.newsapp.ui.domain.usecases.SaveAppEntryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ):LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    )= AppEntryUseCases(
        loadAppEntryUseCase = LoadAppEntryUseCase(localUserManager),
        saveAppEntryUseCase = SaveAppEntryUseCase(localUserManager)
    )
}
package com.example.newsapp.ui.di

import android.app.Application
import com.example.newsapp.ui.data.LocalUserManagerImpl
import com.example.newsapp.ui.data.remote.NewsApi
import com.example.newsapp.ui.data.remote.repository.NewsRepositoryImpl
import com.example.newsapp.ui.domain.manager.LocalUserManager
import com.example.newsapp.ui.domain.repository.NewsRepository
import com.example.newsapp.ui.domain.usecases.app_entry.AppEntryUseCases
import com.example.newsapp.ui.domain.usecases.app_entry.LoadAppEntryUseCase
import com.example.newsapp.ui.domain.usecases.app_entry.SaveAppEntryUseCase
import com.example.newsapp.ui.domain.usecases.news.GetNews
import com.example.newsapp.ui.domain.usecases.news.NewsUseCases
import com.example.newsapp.ui.domain.usecases.news.SearchNewsUseCase
import com.example.newsapp.ui.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ):LocalUserManager = LocalUserManagerImpl(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    )= AppEntryUseCases(
        loadAppEntryUseCase = LoadAppEntryUseCase(localUserManager),
        saveAppEntryUseCase = SaveAppEntryUseCase(localUserManager)
    )

    @Provides
    @Singleton
    fun provideLoadEntryUseCase(
        localUserManager: LocalUserManager
    ) = LoadAppEntryUseCase(localUserManager)


    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi
    ): NewsRepository = NewsRepositoryImpl(newsApi)

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository
    ): NewsUseCases{
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNewsUseCase(newsRepository)
        )
    }
}
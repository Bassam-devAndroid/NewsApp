package com.example.newsapp.di

import android.app.Application
import androidx.room.Room
import com.example.newsapp.data.LocalUserManagerImpl
import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.data.local.NewsDataBase
import com.example.newsapp.data.local.NewsTypeConverter
import com.example.newsapp.domain.manager.LocalUserManager
import com.example.newsapp.data.remote.NewsApi
import com.example.newsapp.data.remote.repository.NewsRepositoryImpl
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.usecases.app_entry.AppEntryUseCases
import com.example.newsapp.domain.usecases.app_entry.LoadAppEntryUseCase
import com.example.newsapp.domain.usecases.app_entry.SaveAppEntryUseCase
import com.example.newsapp.domain.usecases.news.DeleteArticleUseCase
import com.example.newsapp.domain.usecases.news.GetArticlesUseCase
import com.example.newsapp.domain.usecases.news.GetNews
import com.example.newsapp.domain.usecases.news.NewsUseCases
import com.example.newsapp.domain.usecases.news.SearchNewsUseCase
import com.example.newsapp.domain.usecases.news.UpsertUseCase
import com.example.newsapp.util.Constants.BASE_URL
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
    ): LocalUserManager = LocalUserManagerImpl(context = application)

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
        newsRepository: NewsRepository,
        newsDao: NewsDao
    ): NewsUseCases{
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNewsUseCase(newsRepository),
            getArticlesUseCase = GetArticlesUseCase(newsDao),
            upsertUseCase = UpsertUseCase(newsDao),
            deleteArticleUseCase = DeleteArticleUseCase(newsDao)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDataBase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDataBase::class.java,
            name = "news_db"
        ).addTypeConverter(NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDataBase: NewsDataBase
    ) = newsDataBase.newsDao()
}
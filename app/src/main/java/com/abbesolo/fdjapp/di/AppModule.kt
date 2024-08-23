package com.abbesolo.fdjapp.di

import com.abbesolo.fdjapp.data.api.SportsApi
import com.abbesolo.fdjapp.data.repository.SportsRepositoryImpl
import com.abbesolo.fdjapp.domain.repository.SportsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideSportsRepository(api: SportsApi): SportsRepository {
        return SportsRepositoryImpl(api)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

package ru.shtykin.pdauthapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.shtykin.pdauthapp.data.auth_repository.AuthRepositoryImpl
import ru.shtykin.pdauthapp.domain.AuthRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }


}
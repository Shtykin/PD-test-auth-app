package ru.shtykin.pdauthapp.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

//    @Provides
//    fun provideGetBluetoothStateUseCase(repository: Repository): GetBluetoothStateUseCase =
//        GetBluetoothStateUseCase(repository)

}
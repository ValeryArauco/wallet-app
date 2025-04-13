package com.example.myapplication.di

import android.content.Context
import com.example.data.TransaccionRepository
import com.example.data.transaccion.ITransaccionLocalDataSource
import com.example.framework.transaccion.TransaccionLocalDataSource
import com.example.usecases.SaveTransaccion
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSaveTransaccion(repository: TransaccionRepository): SaveTransaccion {
        return SaveTransaccion(repository)
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(@ApplicationContext context: Context): ITransaccionLocalDataSource {
        return TransaccionLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun transaccionRepository(localDataSource: ITransaccionLocalDataSource): TransaccionRepository {
        return TransaccionRepository(localDataSource)
    }

}
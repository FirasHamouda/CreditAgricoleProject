package com.example.creditagricolproject.di

import com.example.creditagricolproject.data.datasource.AccountsApi
import com.example.creditagricolproject.util.urls.URL.BASE_URL
import com.google.gson.GsonBuilder
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
    @Singleton
    @Provides

    fun providesRetrofit() : Retrofit{

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun providesAccountApi(retrofit: Retrofit) : AccountsApi{
        return retrofit.create(AccountsApi::class.java)
    }

}
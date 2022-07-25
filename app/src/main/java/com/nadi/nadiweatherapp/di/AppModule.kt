package com.nadi.nadiweatherapp.di

import android.content.Context
import androidx.room.Room
import com.nadi.data.repository.WeatherRepositoryImplementer
import com.nadi.data.source.local.database.MainDatabase
import com.nadi.data.source.local.database.weather.WeatherDAO
import com.nadi.data.source.remote.openWeatherMap.weather.WeatherAPI
import com.nadi.domain.weather.repository.WeatherRepository
import com.nadi.nadiweatherapp.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor =
        Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder().url(
                    chain.request().url.newBuilder()
                        .addQueryParameter("APIKey", com.nadi.data.BuildConfig.API_KEY)
                        .build()
                ).build()
            )
        }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
            okHttpClientBuilder.addInterceptor(providesInterceptor())
        }
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherAPI = retrofit.create(WeatherAPI::class.java)

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): MainDatabase =
        Room.databaseBuilder(context, MainDatabase::class.java, "MainDatabase")
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesDatabaseDao(db: MainDatabase): WeatherDAO = db.weatherDAO

}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindWeatherRepository(weatherRepositoryImplementer: WeatherRepositoryImplementer): WeatherRepository

}

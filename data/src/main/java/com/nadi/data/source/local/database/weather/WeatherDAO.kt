package com.nadi.data.source.local.database.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nadi.data.source.local.database.weather.entity.DatabaseCity
import com.nadi.data.source.local.database.weather.entity.DatabaseWeather

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: DatabaseCity)

    @Query("SELECT * FROM city")
    suspend fun getCities(): List<DatabaseCity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: DatabaseWeather)

    @Query("SELECT * FROM weather WHERE cityName = :cityName")
    suspend fun getWeatherHistory(cityName: String): List<DatabaseWeather>
}
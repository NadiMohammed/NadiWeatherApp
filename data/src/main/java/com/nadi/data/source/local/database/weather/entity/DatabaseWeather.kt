package com.nadi.data.source.local.database.weather.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nadi.domain.weather.entity.Weather

@Entity(tableName = "weather")
data class DatabaseWeather(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var cityName: String = "",
    var countryCode: String = "",
    var dateTime: String = "",
    var description: String = "",
    var temperature: Double = 0.0,
    var humidity: Int = 0,
    var windspeed: Double = 0.0,
    var icon: String = "",
)

fun Weather.asDatabaseModel(): DatabaseWeather {
    return DatabaseWeather(
        cityName = cityName,
        countryCode= countryCode,
        dateTime = dateTime,
        description = description,
        temperature = temperature,
        humidity = humidity,
        windspeed = windspeed,
        icon = icon
    )
}

fun DatabaseWeather.asDomainModel(): Weather {
    return Weather(
        cityName = cityName,
        countryCode= countryCode,
        dateTime = dateTime,
        description = description,
        temperature = temperature,
        humidity = humidity,
        windspeed = windspeed,
        icon = icon
    )
}

fun List<DatabaseWeather>.asDomainWeather(): List<Weather> =
    map { it.asDomainModel() }
package com.nadi.data.source.local.database.weather.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nadi.domain.weather.entity.City

@Entity(tableName = "city")
data class DatabaseCity(
   @PrimaryKey val name: String
)

fun City.asDatabaseModel(): DatabaseCity =
    DatabaseCity(name)

fun DatabaseCity.asDomainModel(): City =
    City(name)

fun List<DatabaseCity>.asDomainCity(): List<City> =
    map { it.asDomainModel() }
package com.nadi.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nadi.data.source.local.database.weather.WeatherDAO
import com.nadi.data.source.local.database.weather.entity.DatabaseCity
import com.nadi.data.source.local.database.weather.entity.DatabaseWeather
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(
    entities = [DatabaseCity::class, DatabaseWeather::class],
    version = 1,
    exportSchema = false
)
abstract class MainDatabase : RoomDatabase() {

    abstract val weatherDAO: WeatherDAO

    companion object {

        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getInstance(@ApplicationContext context: Context): MainDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MainDatabase::class.java,
                        "MainDatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
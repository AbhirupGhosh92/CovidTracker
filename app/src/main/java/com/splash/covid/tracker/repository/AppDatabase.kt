package com.splash.covid.tracker.repository

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.splash.covid.tracker.repository.dao.DistDao
import com.splash.covid.tracker.repository.dao.GraphDao
import com.splash.covid.tracker.repository.dao.ResponseDao
import com.splash.covid.tracker.repository.entity.DistrictEntity
import com.splash.covid.tracker.repository.entity.GraphEntity
import com.splash.covid.tracker.repository.entity.ResponseEntity
import com.splash.covid.tracker.repository.typecovertors.CustomTypeCovertor

@Database(
    entities = [ResponseEntity::class,GraphEntity::class,DistrictEntity::class],
    version = 3,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private var INSTANCE: AppDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "covid_tracker.db"
                        ).addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                Log.d("covid_tracker.db", "Database created")
                                super.onCreate(db)
                            }

                            override fun onOpen(db: SupportSQLiteDatabase) {
                                Log.d("covid_trackers.db", "Database opened")
                                super.onOpen(db)
                            }
                        })
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }


    abstract fun responseDao(): ResponseDao
    abstract fun graphDao(): GraphDao
    abstract fun distDao():DistDao
}
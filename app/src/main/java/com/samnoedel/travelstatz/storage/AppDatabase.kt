package com.samnoedel.travelstatz.storage

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

const val DATABASE_VERSION = 1

/**
 * © 2018 Sam Noedel
 */
@Database(version = DATABASE_VERSION, entities = [
        Trip::class,
        Location::class
])
abstract class AppDatabase : RoomDatabase() {

    abstract fun tripDao(): TripDao

    abstract fun locationDao(): LocationDao
}

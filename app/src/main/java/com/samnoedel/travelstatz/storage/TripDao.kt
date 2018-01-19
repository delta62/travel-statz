package com.samnoedel.travelstatz.storage

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert

/**
 * © 2018 Sam Noedel
 */
@Dao
interface TripDao {
    @Insert
    fun insertTrip(trip: Trip)
}

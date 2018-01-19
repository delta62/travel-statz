package com.samnoedel.travelstatz.storage

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert

/**
 * © 2018 Sam Noedel
 */
@Dao
interface LocationDao {
    @Insert
    fun insertLocation(location: Location)
}

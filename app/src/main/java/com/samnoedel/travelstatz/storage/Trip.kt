package com.samnoedel.travelstatz.storage

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * © 2018 Sam Noedel
 */

@Entity
class Trip constructor(
        @PrimaryKey
        @ColumnInfo(name = "start_time")
        val startTime: Long)

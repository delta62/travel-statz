package com.samnoedel.travelstatz.storage

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * © 2018 Sam Noedel
 */
@Entity(
        foreignKeys = [
            ForeignKey(entity = Trip::class,
                    parentColumns = ["start_time"],
                    childColumns = ["trip_start"])
        ]
)
class Location constructor(
        @PrimaryKey
        val timestamp: Long,

        @ColumnInfo(name = "trip_start")
        val tripStart: Long,

        val latitude: Double,

        val longitude: Double,

        val accuracy: Float,

        val altitude: Double,

        val bearing: Float,

        @ColumnInfo(name = "bearing_accuracy_degrees")
        val bearingAccuracyDegrees: Float?,

        @ColumnInfo(name = "elapsed_realtime_nanos")
        val elapsedRealtimeNanos: Long?,

        val speed: Float,

        @ColumnInfo(name = "speed_accuracy_meters_per_second")
        val speedAccuracyMetersPerSecond: Float?,

        @ColumnInfo(name = "vertical_accuracy_meters")
        val verticalAccuracyMeters: Float?
)

package com.samnoedel.travelstatz.services

import android.annotation.SuppressLint
import android.location.Location
import android.os.Build

fun mapLocation(location: Location, tripStart: Long): com.samnoedel.travelstatz.storage.Location {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
        mapApi15Location(location, tripStart)
    } else {
        mapApi4Location(location, tripStart)
    }
}

@SuppressLint("NewApi")
private fun mapApi15Location(location: Location, tripStart: Long): com.samnoedel.travelstatz.storage.Location {
    return com.samnoedel.travelstatz.storage.Location(
            location.time,
            tripStart,
            location.latitude,
            location.longitude,
            location.accuracy,
            location.altitude,
            location.bearing,
            location.bearingAccuracyDegrees,
            location.elapsedRealtimeNanos,
            location.speed,
            location.speedAccuracyMetersPerSecond,
            location.verticalAccuracyMeters
    )
}

private fun mapApi4Location(location: Location, tripStart: Long): com.samnoedel.travelstatz.storage.Location {
    return com.samnoedel.travelstatz.storage.Location(
            location.time,
            tripStart,
            location.latitude,
            location.longitude,
            location.accuracy,
            location.altitude,
            location.bearing,
            null,
            null,
            location.speed,
            null,
            null
    )
}

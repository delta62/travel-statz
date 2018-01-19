package com.samnoedel.travelstatz.formatters

import android.content.Context
import android.location.Location
import android.text.format.DateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * © 2018 Sam Noedel
 */

@Singleton
class LocationFormatter @Inject constructor() {

    @Inject lateinit var context: Context

    fun format(location: Location): String {
        val timeFmt = DateFormat.getTimeFormat(context)
        val timeStr = timeFmt.format(Date(location.time))
        return String.format("[ %s ]: %f %f\n", timeStr, location.latitude, location.longitude)
    }
}

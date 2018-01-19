package com.samnoedel.travelstatz.services

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.samnoedel.travelstatz.Config
import com.samnoedel.travelstatz.error.PermissionError
import com.samnoedel.travelstatz.event.TripStartedEvent
import io.reactivex.Observer
import javax.inject.Inject
import javax.inject.Singleton

/**
 * © 2017 Sam Noedel
 */

@Singleton
class GpsService @Inject constructor(
        private val context: Context,
        private val gpsClient: FusedLocationProviderClient,
        private val tripStartedObserver: Observer<TripStartedEvent>,
        private val locationObserver: Observer<Location>
) {

    companion object {
        private val TAG = "GpsService"
    }

    private var locationCallback: LocationCallback? = null

    @SuppressLint("MissingPermission")
    @Synchronized
    fun startGpsUpdates() {
        if (locationCallback != null) return

        if (!hasPermission()) {
            locationObserver.onError(PermissionError("GPS permission denied"))
            return
        }

        val event = TripStartedEvent(System.currentTimeMillis())
        tripStartedObserver.onNext(event)

        val request = LocationRequest()
        request.interval = Config.LOCATION_FREQ_MS

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                result?.lastLocation?.let { loc -> locationObserver.onNext(loc) }
            }
        }

        gpsClient.requestLocationUpdates(request, locationCallback, null)
    }

    @Synchronized
    fun stopGpsUpdates() {
        gpsClient.removeLocationUpdates(locationCallback)
        locationCallback = null
    }

    private fun hasPermission(): Boolean {
        val permission = ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION)

        return permission == PackageManager.PERMISSION_GRANTED
    }
}

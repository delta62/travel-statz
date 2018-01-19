package com.samnoedel.travelstatz.services

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import io.reactivex.Observable
import javax.inject.Inject

/**
 * © 2018 Sam Noedel
 */
class PermissionChecker @Inject constructor() {

    companion object {
        val GPS_PERMISSION_REQUEST_CODE = 0
    }

    fun hasPermission(activity: Activity, permission: String): Observable<Boolean> {
        val permissionState = ContextCompat.checkSelfPermission(activity, permission)
        return Observable.just(permissionState == PackageManager.PERMISSION_GRANTED)
    }

    fun requestGpsPermission(activity: Activity, permission: String) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), GPS_PERMISSION_REQUEST_CODE)
    }
}

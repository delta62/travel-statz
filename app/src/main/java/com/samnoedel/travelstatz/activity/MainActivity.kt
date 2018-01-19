package com.samnoedel.travelstatz.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.samnoedel.travelstatz.R
import com.samnoedel.travelstatz.fragment.MainFragment
import com.samnoedel.travelstatz.services.PermissionChecker
import com.samnoedel.travelstatz.services.PermissionChecker.Companion.GPS_PERMISSION_REQUEST_CODE
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * © 2017 Sam Noedel
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    @Inject lateinit var permissionChecker: PermissionChecker

    private val mainFragment = MainFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<NavigationView>(R.id.nav_drawer)
                .setNavigationItemSelectedListener {
                    Log.d(TAG, "onCLickY")
                    true
                }

        supportFragmentManager
                .beginTransaction()
                .add(R.id.main_fragment_container, mainFragment)
                .commit()

        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        permissionChecker.hasPermission(this, permission)
                .filter { granted -> !granted }
                .forEach { permissionChecker.requestGpsPermission(this, permission) }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            GPS_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "GPS permissions granted")
                } else {
                    Log.e(TAG, "GPS permission denied")
                }
            }
        }
    }
}

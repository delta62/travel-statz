package com.samnoedel.travelstatz.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.samnoedel.travelstatz.R
import com.samnoedel.travelstatz.fragment.TripListFragment

class TripHistoryActivity : AppCompatActivity() {

    private val tripListFragment = TripListFragment.newInstance(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_history)

        supportFragmentManager
                .beginTransaction()
                .add(R.id.trip_list, tripListFragment)
                .commit()
    }
}

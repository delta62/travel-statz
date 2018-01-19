package com.samnoedel.travelstatz.dagger

import com.samnoedel.travelstatz.activity.MainActivity
import com.samnoedel.travelstatz.fragment.MainFragment
import com.samnoedel.travelstatz.TravelStatzService
import com.samnoedel.travelstatz.fragment.TripListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * © 2017 Sam Noedel
 */

@Module
abstract class AndroidAppModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeMainFragmentInjector(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeServiceInjector(): TravelStatzService

    @ContributesAndroidInjector
    abstract fun contributeTripListFragmentInjector(): TripListFragment
}

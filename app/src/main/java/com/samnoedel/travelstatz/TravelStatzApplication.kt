package com.samnoedel.travelstatz

import android.app.Activity
import android.app.Application
import android.app.Service
import android.location.Location
import android.support.v4.app.Fragment
import com.samnoedel.travelstatz.dagger.DaggerAppComponent
import com.samnoedel.travelstatz.event.TripStartedEvent
import com.samnoedel.travelstatz.event.TripStoppedEvent
import com.samnoedel.travelstatz.receiver.LocationDbReceiver
import com.samnoedel.travelstatz.receiver.TripStartedDbReceiver
import com.samnoedel.travelstatz.receiver.TripStartedServiceReceiver
import com.samnoedel.travelstatz.receiver.TripStoppedServiceReceiver
import com.samnoedel.travelstatz.services.NotificationChannelBuilder
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.Observable
import javax.inject.Inject

/**
 * © 2017 Sam Noedel
 */
class TravelStatzApplication : Application(),
        HasActivityInjector,
        HasSupportFragmentInjector,
        HasServiceInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    @Inject lateinit var tripStoppedServiceReceiver: TripStoppedServiceReceiver
    @Inject lateinit var tripStartedServiceReceiver: TripStartedServiceReceiver
    @Inject lateinit var tripStartedDbReceiver: TripStartedDbReceiver
    @Inject lateinit var locationDbReceiver: LocationDbReceiver

    @Inject lateinit var locationUpdatedStream: Observable<Location>
    @Inject lateinit var tripStartedStream: Observable<TripStartedEvent>
    @Inject lateinit var tripStoppedStream: Observable<TripStoppedEvent>

    @Inject lateinit var notificationChannelBuilder: NotificationChannelBuilder

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .withContext(this)
                .build()
                .inject(this)

        notificationChannelBuilder.initNotificationChannel()

        tripStartedDbReceiver.startListening(tripStartedStream)
        tripStartedServiceReceiver.startListening(tripStartedStream)
        tripStoppedServiceReceiver.startListening(tripStoppedStream)
        locationDbReceiver.startListening(locationUpdatedStream)
    }

    override fun onTerminate() {
        tripStartedDbReceiver.stopListening()
        tripStartedServiceReceiver.stopListening()
        tripStoppedServiceReceiver.stopListening()
        locationDbReceiver.stopListening()
        super.onTerminate()
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return serviceInjector
    }
}

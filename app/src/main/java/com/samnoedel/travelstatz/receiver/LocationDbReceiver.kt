package com.samnoedel.travelstatz.receiver

import android.location.Location
import com.samnoedel.travelstatz.event.TripStartedEvent
import com.samnoedel.travelstatz.services.mapLocation
import com.samnoedel.travelstatz.storage.LocationDao
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import javax.inject.Singleton

typealias DbLocation = com.samnoedel.travelstatz.storage.Location
typealias LocationZipper = BiFunction<Location, TripStartedEvent, DbLocation>

/**
 * © 2018 Sam Noedel
 */
@Singleton
class LocationDbReceiver @Inject constructor(
        private val locationDao: LocationDao,
        private val tripStartObservable: Observable<TripStartedEvent>,
        private val executor: ExecutorService) : Receiver<Location> {

    companion object {
        private val TAG = "LocationDbReceiver"
    }

    private var subscription: Disposable? = null

    @Synchronized
    override fun startListening(observable: Observable<Location>) {
        if (subscription != null) return

        subscription = observable
                .withLatestFrom(tripStartObservable, LocationZipper { location, tripStart ->
                    mapLocation(location, tripStart.timestamp)
                })
                .subscribe({ location ->
                    executor.execute { locationDao.insertLocation(location) }
                })
    }

    override fun stopListening() {
        subscription?.dispose()
    }
}

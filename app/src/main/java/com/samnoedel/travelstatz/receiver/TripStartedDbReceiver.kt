package com.samnoedel.travelstatz.receiver

import com.samnoedel.travelstatz.event.TripStartedEvent
import com.samnoedel.travelstatz.storage.Trip
import com.samnoedel.travelstatz.storage.TripDao
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * © 2018 Sam Noedel
 */

@Singleton
class TripStartedDbReceiver @Inject constructor(
        private val tripDao: TripDao,
        private val executor: ExecutorService
) : Receiver<TripStartedEvent> {

    private var subscription: Disposable? = null

    @Synchronized
    override fun startListening(observable: Observable<TripStartedEvent>) {
        if (subscription != null) return

        subscription = observable.subscribe { event ->
            val trip = Trip(event.timestamp)
            executor.execute { tripDao.insertTrip(trip) }
        }
    }

    @Synchronized
    override fun stopListening() {
        subscription?.dispose()
    }
}

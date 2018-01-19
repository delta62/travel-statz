package com.samnoedel.travelstatz.receiver

import android.content.Context
import android.content.Intent
import com.samnoedel.travelstatz.TravelStatzService
import com.samnoedel.travelstatz.event.TripStoppedEvent
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * © 2018 Sam Noedel
 */
@Singleton
class TripStoppedServiceReceiver @Inject constructor(
        private val context: Context
) : Receiver<TripStoppedEvent> {

    private var subscription: Disposable? = null

    @Synchronized
    override fun startListening(observable: Observable<TripStoppedEvent>) {
        if (subscription != null) return

        subscription = observable.subscribe {
            val intent = Intent(context, TravelStatzService::class.java)
            context.stopService(intent)
        }
    }

    @Synchronized
    override fun stopListening() {
        subscription?.dispose()
    }
}

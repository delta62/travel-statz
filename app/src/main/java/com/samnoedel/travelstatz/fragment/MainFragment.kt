package com.samnoedel.travelstatz.fragment

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.samnoedel.travelstatz.R
import com.samnoedel.travelstatz.TravelStatzService
import com.samnoedel.travelstatz.event.TripStartedEvent
import com.samnoedel.travelstatz.event.TripStoppedEvent
import com.samnoedel.travelstatz.formatters.LocationFormatter
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * © 2017 Sam Noedel
 */
class MainFragment : Fragment() {
    companion object {
        private val TAG = "MainFragment"

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    @Inject lateinit var locationObservable: Observable<Location>
    @Inject lateinit var tripStoppedObservable: Observer<TripStoppedEvent>
    @Inject lateinit var tripStartedObserver: Observer<TripStartedEvent>
    @Inject lateinit var locationFormatter: LocationFormatter

    private var button: Button? = null
    private var textView: TextView? = null
    private var subscription: Disposable? = null

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_main, container, false)

        textView = view?.findViewById(R.id.textView)
        textView?.movementMethod = ScrollingMovementMethod()

        button = view?.findViewById(R.id.startTripButton)
        button?.setOnClickListener({
            if (TravelStatzService.isRunning) {
                tripStoppedObservable.onNext(TripStoppedEvent())
                button?.setText(R.string.label_start_trip)
            } else {
                tripStartedObserver.onNext(TripStartedEvent(System.currentTimeMillis()))
                textView?.text = ""
                button?.setText(R.string.label_stop_trip)
            }
        })

        subscription = locationObservable.subscribe { loc ->
            textView?.append(locationFormatter.format(loc!!))
        }

        return view
    }

    override fun onDestroy() {
        subscription?.dispose()
        super.onDestroy()
    }
}

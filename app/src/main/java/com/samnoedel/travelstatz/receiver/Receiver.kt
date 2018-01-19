package com.samnoedel.travelstatz.receiver

import io.reactivex.Observable

/**
 * © 2018 Sam Noedel
 */
interface Receiver<T> {
    fun startListening(observable: Observable<T>)

    fun stopListening()
}

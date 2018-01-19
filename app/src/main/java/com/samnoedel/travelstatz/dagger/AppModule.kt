package com.samnoedel.travelstatz.dagger

import android.app.NotificationManager
import android.arch.persistence.room.Room
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.samnoedel.travelstatz.Config
import com.samnoedel.travelstatz.event.TripStartedEvent
import com.samnoedel.travelstatz.event.TripStoppedEvent
import com.samnoedel.travelstatz.storage.AppDatabase
import com.samnoedel.travelstatz.storage.LocationDao
import com.samnoedel.travelstatz.storage.TripDao
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * © 2017 Sam Noedel
 */

@Module
class AppModule {

    private val locationSubject: Subject<Location>
    private val tripStartedSubject: Subject<TripStartedEvent>
    private val tripStoppedSubject: Subject<TripStoppedEvent>

    init {
        locationSubject = PublishSubject.create()
        tripStartedSubject = PublishSubject.create()
        tripStoppedSubject = PublishSubject.create()
    }

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, Config.DB_NAME).build()
    }

    @Singleton
    @Provides
    fun provideLocationDao(db: AppDatabase): LocationDao {
        return db.locationDao()
    }

    @Singleton
    @Provides
    fun provideTripDao(db: AppDatabase): TripDao {
        return db.tripDao()
    }

    @Singleton
    @Provides
    fun provideLocationObservable(): Observable<Location> {
        return locationSubject
    }

    @Singleton
    @Provides
    fun provideLocationObserver(): Observer<Location> {
        return locationSubject
    }

    @Singleton
    @Provides
    fun provideTripStartedObserver(): Observer<TripStartedEvent> {
        return tripStartedSubject
    }

    @Singleton
    @Provides
    fun provideTripStartedObservable(): Observable<TripStartedEvent> {
        return tripStartedSubject
    }

    @Singleton
    @Provides
    fun provideTripStoppedObservable(): Observable<TripStoppedEvent> {
        return tripStoppedSubject
    }

    @Singleton
    @Provides
    fun provideTripStoppedObserver(): Observer<TripStoppedEvent> {
        return tripStoppedSubject
    }

    @Singleton
    @Provides
    fun provideNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Singleton
    @Provides
    fun provideExecutorService(): ExecutorService {
        return Executors.newSingleThreadExecutor()
    }
}

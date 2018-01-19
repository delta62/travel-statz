package com.samnoedel.travelstatz.dagger

import android.content.Context
import com.samnoedel.travelstatz.TravelStatzApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * © 2017 Sam Noedel
 */

@Singleton
@Component(modules = [
        AndroidSupportInjectionModule::class,
        AndroidAppModule::class,
        AppModule::class
])
interface AppComponent : AndroidInjector<TravelStatzApplication> {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun withContext(context: Context): Builder
    }
}

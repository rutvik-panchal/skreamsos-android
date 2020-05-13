package com.rutvik.apps.skreamsos

import android.app.Application
import com.rutvik.apps.skreamsos.dagger.ApplicationComponent
import com.rutvik.apps.skreamsos.dagger.DaggerApplicationComponent

class SkreamSOS: Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.create()
    }

}

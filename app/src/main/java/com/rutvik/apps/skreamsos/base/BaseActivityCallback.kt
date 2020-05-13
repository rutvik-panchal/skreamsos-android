package com.rutvik.apps.skreamsos.base

import android.widget.ProgressBar
import com.rutvik.apps.skreamsos.dagger.ApplicationComponent

interface BaseActivityCallback {

    fun getApplicationComponent(): ApplicationComponent

    fun showToastShort(message: String)

    fun showToastLong(message: String)

    fun setToolbarTitle(title: String)

    fun showProgress(progressBar: ProgressBar?)

    fun hideProgress(progressBar: ProgressBar?)
}
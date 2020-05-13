package com.rutvik.apps.skreamsos.base

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rutvik.apps.skreamsos.SkreamSOS
import com.rutvik.apps.skreamsos.dagger.ApplicationComponent

open class BaseActivity : AppCompatActivity(), BaseActivityCallback {

    companion object {
        const val TAG = "BaseActivity"
    }

    override fun getApplicationComponent(): ApplicationComponent =
        (applicationContext as SkreamSOS).appComponent

    override fun showToastShort(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    override fun showToastLong(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    override fun setToolbarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar?.title = title
        }
    }

    override fun showProgress(progressBar: ProgressBar?) {
        if (progressBar != null) {
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgress(progressBar: ProgressBar?) {
        if (progressBar != null) {
            progressBar.visibility = View.GONE
        }
    }

}
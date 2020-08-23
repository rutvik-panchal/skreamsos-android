package com.rutvik.apps.skreamsos.home

import android.util.Log
import com.rutvik.apps.skreamsos.api.ApiClient
import com.rutvik.apps.skreamsos.api.models.SOSAlert
import com.rutvik.apps.skreamsos.api.models.SOSResponse
import com.rutvik.apps.skreamsos.utils.FirebaseHelper
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomePresenter @Inject constructor(): HomeContract.HomePresenter {

    private var homeView: HomeContract.HomeView? = null

    override fun attachView(view: HomeContract.HomeView) {
        this.homeView = view
    }

    override fun detachView() {
        this.homeView = null
    }

    override fun sendSOS(sosAlert: SOSAlert) {
        Log.d("HomePresenter", "sendSOS called")
        val callback = object : Callback<SOSResponse> {
            override fun onResponse(call: retrofit2.Call<SOSResponse>, response: Response<SOSResponse>) {
                Log.d("HomePresenter", response.body()?.message.toString())
            }

            override fun onFailure(call: retrofit2.Call<SOSResponse>, t: Throwable) {
                Log.d("HomePresenter", "API Failed")
            }
        }
        ApiClient.sendSOSAlert(callback, sosAlert)
    }

    override fun signOut() {
        FirebaseHelper.signOut()
        homeView?.signOutUser()
    }
}
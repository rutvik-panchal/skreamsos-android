package com.rutvik.apps.skreamsos.home

<<<<<<< Updated upstream
=======
import android.util.Log
import com.rutvik.apps.skreamsos.api.ApiClient
import com.rutvik.apps.skreamsos.api.models.response.ResponseMessage
import com.rutvik.apps.skreamsos.api.models.SOSAlert
import com.rutvik.apps.skreamsos.api.models.response.SOSResponse
>>>>>>> Stashed changes
import com.rutvik.apps.skreamsos.utils.FirebaseHelper
import javax.inject.Inject

class HomePresenter @Inject constructor(): HomeContract.HomePresenter {

    private var homeView: HomeContract.HomeView? = null

    override fun signOut() {
        FirebaseHelper.signOut()
        homeView?.signOutUser()
    }

    override fun attachView(view: HomeContract.HomeView) {
        this.homeView = view
    }

    override fun detachView() {
        this.homeView = null
    }

<<<<<<< Updated upstream
=======
    override fun sendSOS(sosAlert: SOSAlert) {
        Log.d("HomePresenter", "sendSOS() called")
        val callback = object : Callback<SOSResponse> {
            override fun onResponse(call: retrofit2.Call<SOSResponse>, response: Response<SOSResponse>) {
                Log.d("HomePresenter", response.body()?.message.toString() + response.body()?.doc?.location?.toString())
            }

            override fun onFailure(call: retrofit2.Call<SOSResponse>, t: Throwable) {
                Log.d("HomePresenter", "sendSOS() API Failed ")
            }
        }
        ApiClient.sendSOSAlert(callback, sosAlert)
    }

    override fun deleteSOS() {
        Log.d("HomePresenter", "deleteSOS() called")
        val callback = object : Callback<ResponseMessage> {
            override fun onResponse(call: retrofit2.Call<ResponseMessage>, response: Response<ResponseMessage>) {
                Log.d("HomePresenter", response.body()?.message.toString())
            }

            override fun onFailure(call: retrofit2.Call<ResponseMessage>, t: Throwable) {
                Log.d("HomePresenter", "deleteSOS() : API Failed")
            }
        }
        ApiClient.deleteSOSAlert(callback)
    }

    override fun signOut() {
        FirebaseHelper.signOut()
        homeView?.signOutUser()
    }
>>>>>>> Stashed changes
}
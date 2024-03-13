package com.rutvik.apps.skreamsos.home

import com.rutvik.apps.skreamsos.base.BasePresenter
import com.rutvik.apps.skreamsos.base.BaseView
import com.rutvik.apps.skreamsos.api.models.SOSAlert
import okhttp3.MultipartBody
import java.io.InputStream

interface HomeContract {

    interface HomeView: BaseView<HomePresenter>{
        fun sendSOS()
        fun deleteSOS()
        fun updateHeaderUI()
        fun signOutUser()
    }

    interface HomePresenter: BasePresenter<HomeView>{
        fun sendSOS(sosAlert: SOSAlert)
        fun deleteSOS()
        fun signOut()
        fun sendSOSImage(image: MultipartBody.Part)
    }

}
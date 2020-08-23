package com.rutvik.apps.skreamsos.home

import com.rutvik.apps.skreamsos.base.BasePresenter
import com.rutvik.apps.skreamsos.base.BaseView
import com.rutvik.apps.skreamsos.api.models.SOSAlert

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
    }

}
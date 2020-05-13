package com.rutvik.apps.skreamsos.home

import com.rutvik.apps.skreamsos.base.BasePresenter
import com.rutvik.apps.skreamsos.base.BaseView

interface HomeContract {

    interface HomeView: BaseView<HomePresenter>{
        fun updateHeaderUI()
        fun signOutUser()
    }

    interface HomePresenter: BasePresenter<HomeView>{
        fun signOut()
    }

}
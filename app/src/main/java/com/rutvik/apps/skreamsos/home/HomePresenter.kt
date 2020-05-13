package com.rutvik.apps.skreamsos.home

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

}
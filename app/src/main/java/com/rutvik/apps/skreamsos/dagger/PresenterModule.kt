package com.rutvik.apps.skreamsos.dagger

import com.rutvik.apps.skreamsos.login.LoginPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun loginPresenter(): LoginPresenter = LoginPresenter()

}
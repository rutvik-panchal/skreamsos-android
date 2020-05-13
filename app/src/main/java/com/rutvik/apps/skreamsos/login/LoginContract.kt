package com.rutvik.apps.skreamsos.login

import com.rutvik.apps.skreamsos.base.BasePresenter
import com.rutvik.apps.skreamsos.base.BaseView

interface LoginContract {

    interface LoginPresenter : BasePresenter<LoginView> {
        fun login(email: String, password: String)
    }

    interface LoginView : BaseView<LoginPresenter> {
        fun loginSuccess()
        fun loginFailed(error: String)
        fun goToRegister()
    }
}
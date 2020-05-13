package com.rutvik.apps.skreamsos.login

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.rutvik.apps.skreamsos.utils.FirebaseHelper
import javax.inject.Inject

class LoginPresenter @Inject constructor() : LoginContract.LoginPresenter {

    companion object {
        const val TAG = "LoginPresenter"
    }

    private var loginView : LoginContract.LoginView? = null

    override fun login(email: String, password:String) {
        FirebaseHelper.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                loginView?.loginSuccess()
            } else {
                loginView?.loginFailed("Login Failed!")
            }
        }
    }

    override fun attachView(view: LoginContract.LoginView) {
        this.loginView = view
    }

    override fun detachView() {
        this.loginView = null
    }


}
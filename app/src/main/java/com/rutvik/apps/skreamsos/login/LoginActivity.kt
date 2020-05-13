package com.rutvik.apps.skreamsos.login

import android.content.Intent
import android.os.Bundle
import com.rutvik.apps.skreamsos.R
import com.rutvik.apps.skreamsos.base.BaseActivity
import com.rutvik.apps.skreamsos.home.HomeActivity
import com.rutvik.apps.skreamsos.utils.FirebaseHelper
import com.rutvik.apps.skreamsos.utils.SharedPreferenceHelper
import com.rutvik.apps.skreamsos.utils.constants.PrefConstants
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginContract.LoginView {

    companion object {
        const val TAG = "LoginActivity"
    }

    @Inject lateinit var presenter: LoginPresenter
    @Inject lateinit var sharedPref : SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApplicationComponent().inject(this)
        setContentView(R.layout.activity_login)

        sharedPref.init(this)
        setClickListeners()
        presenter.attachView(this)
    }

    private fun setClickListeners() {
        loginButton.setOnClickListener {
            val email = emailIdEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                showProgress(progressbar)
                presenter.login(email, password)
            } else {
                showToastShort("Please fill all the fields!")
            }
        }
        registerUserTextView.setOnClickListener {
            goToRegister()
        }
    }

    override fun loginSuccess() {
        val user = FirebaseHelper.getUser()
        if (user != null) {
            sharedPref.write(PrefConstants.HAS_USER_DETAILS, true)
            sharedPref.write(PrefConstants.USER_UID, user.uid)
            sharedPref.write(PrefConstants.USER_NAME, user.displayName!!)
            sharedPref.write(PrefConstants.USER_EMAIL, user.email!!)
            sharedPref.write(PrefConstants.USER_PHONE_NO, user.phoneNumber!!)
        }
        hideProgress(progressbar)
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun loginFailed(error: String) {
        hideProgress(progressbar)
        showToastLong(error)
    }

    override fun goToRegister() {
        showToastLong("Coming Soon!")
        //startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}

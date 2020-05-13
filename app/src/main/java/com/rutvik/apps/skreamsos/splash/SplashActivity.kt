package com.rutvik.apps.skreamsos.splash

import android.content.Intent
import android.os.Bundle
import com.rutvik.apps.skreamsos.R
import com.rutvik.apps.skreamsos.base.BaseActivity
import com.rutvik.apps.skreamsos.home.HomeActivity
import com.rutvik.apps.skreamsos.login.LoginActivity
import com.rutvik.apps.skreamsos.onboarding.OnBoardingActivity
import com.rutvik.apps.skreamsos.utils.FirebaseHelper
import com.rutvik.apps.skreamsos.utils.SharedPreferenceHelper
import com.rutvik.apps.skreamsos.utils.constants.PrefConstants
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject lateinit var prefHelper: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApplicationComponent().inject(this)
        setContentView(R.layout.activity_splash)

        prefHelper.init(this)

        if (FirebaseHelper.getUser() != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            if (prefHelper.readBoolean(PrefConstants.ON_BOARDED)!!) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            }
        }
    }

}

package com.rutvik.apps.skreamsos.onboarding

import android.content.Intent
import android.os.Bundle
import com.rutvik.apps.skreamsos.R
import com.rutvik.apps.skreamsos.base.BaseActivity
import com.rutvik.apps.skreamsos.login.LoginActivity
import com.rutvik.apps.skreamsos.utils.SharedPreferenceHelper
import com.rutvik.apps.skreamsos.utils.constants.PrefConstants
import kotlinx.android.synthetic.main.activity_on_boarding.*
import javax.inject.Inject

class OnBoardingActivity : BaseActivity(), OnBoardingView {

    @Inject lateinit var sharedPrefs: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApplicationComponent().inject(this)
        setContentView(R.layout.activity_on_boarding)

        sharedPrefs.init(this)
        viewPager.adapter = OnBoardingViewPagerAdapter(data)
    }

    private val data by lazy {
        listOf<OnBoard>(
        OnBoard(
            getString(R.string.test),
            getString(R.string.onboard_one_description), R.drawable.ic_onboard_one
        ),
        OnBoard(
            getString(R.string.onboard_two_title),
            getString(R.string.onboard_two_desc), R.drawable.ic_onboard_two
        ),
        OnBoard(
            getString(R.string.onboard_three_title),
            getString(R.string.onboard_three_description), R.drawable.ic_onboard_three
        ))
    }

    override fun onNextClick() {
        viewPager.currentItem += 1
    }

    override fun onBackClick() {
        viewPager.currentItem -= 1
    }

    override fun onContinueClick() {
        sharedPrefs.write(PrefConstants.ON_BOARDED, true)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}

package com.rutvik.apps.skreamsos.register

import android.os.Bundle
import com.rutvik.apps.skreamsos.R
import com.rutvik.apps.skreamsos.base.BaseActivity
import javax.inject.Inject

class RegisterActivity : BaseActivity(), RegisterContract.RegisterView {

    @Inject
    lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getApplicationComponent().inject(this)
        setContentView(R.layout.activity_register)
    }

}

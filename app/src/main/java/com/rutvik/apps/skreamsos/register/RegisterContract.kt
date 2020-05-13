package com.rutvik.apps.skreamsos.register

import com.rutvik.apps.skreamsos.base.BasePresenter
import com.rutvik.apps.skreamsos.base.BaseView

interface RegisterContract {

    interface RegisterView: BaseView<RegisterPresenter> {

    }

    interface RegisterPresenter: BasePresenter<RegisterView> {

    }

}
package com.rutvik.apps.skreamsos.dagger

import com.rutvik.apps.skreamsos.home.HomeActivity
import com.rutvik.apps.skreamsos.login.LoginActivity
import com.rutvik.apps.skreamsos.onboarding.OnBoardingActivity
import com.rutvik.apps.skreamsos.register.RegisterActivity
import com.rutvik.apps.skreamsos.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, PresenterModule::class])
interface ApplicationComponent {

    fun inject(loginActivity: LoginActivity)

    fun inject(registerActivity: RegisterActivity)

    fun inject(homeActivity: HomeActivity)

    fun inject(onBoardingActivity: OnBoardingActivity)

    fun inject(splashActivity: SplashActivity)

}
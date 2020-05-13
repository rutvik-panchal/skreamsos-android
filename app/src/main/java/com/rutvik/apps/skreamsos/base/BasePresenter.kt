package com.rutvik.apps.skreamsos.base

interface BasePresenter<in T> {

    fun attachView(view: T)

    fun detachView()

}
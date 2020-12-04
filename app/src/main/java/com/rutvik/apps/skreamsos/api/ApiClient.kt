package com.rutvik.apps.skreamsos.api

import com.rutvik.apps.skreamsos.api.models.response.ResponseMessage
import com.rutvik.apps.skreamsos.api.models.SOSAlert
import com.rutvik.apps.skreamsos.api.models.response.SOSResponse
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://skreamsos.herokuapp.com/"

    private const val token =
        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwaG9uZU51bWJlciI6Iis5MTk5MDAwMzMzMzMiLCJ1c2VySWQiOiI1ZjQyNDIxZDg4MGJiYzAwMTdjZTcwNjMiLCJpYXQiOjE1OTgxODg0OTR9.A8uZgATvlA4BW7ORuC0Cp5WaXZvKM1uEzTOw2JikZFo"

    private fun getClient(): ApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiInterface::class.java)
    }

    fun sendSOSAlert(callback: Callback<SOSResponse>, sos: SOSAlert) {
        val call = getClient().sendSOS(token, sos)
        call.enqueue(callback)
    }

    fun deleteSOSAlert(callback: Callback<ResponseMessage>) {
        val call = getClient().deleteSOS(token)
        call.enqueue(callback)
    }
}
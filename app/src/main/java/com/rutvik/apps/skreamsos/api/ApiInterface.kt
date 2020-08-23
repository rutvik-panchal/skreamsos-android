package com.rutvik.apps.skreamsos.api

import com.rutvik.apps.skreamsos.api.constants.Endpoints
import com.rutvik.apps.skreamsos.api.models.ResponseMessage
import com.rutvik.apps.skreamsos.api.models.SOSAlert
import com.rutvik.apps.skreamsos.api.models.SOSResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    @POST(Endpoints.SEND_SOS)
    fun sendSOS(
        @Header("Authorization") token: String,
        @Body alert: SOSAlert
    ) : Call<SOSResponse>

    @DELETE(Endpoints.DELETE_SOS)
    fun deleteSOS(
        @Header("Authorization") token: String
    ) : Call<ResponseMessage>
}
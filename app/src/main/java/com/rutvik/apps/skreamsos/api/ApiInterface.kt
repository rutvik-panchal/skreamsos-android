package com.rutvik.apps.skreamsos.api

import com.rutvik.apps.skreamsos.api.constants.Endpoints
import com.rutvik.apps.skreamsos.api.models.ResponseMessage
import com.rutvik.apps.skreamsos.api.models.SOSAlert
import com.rutvik.apps.skreamsos.api.models.SOSResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


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

    @Multipart
    @POST(Endpoints.SEND_SOS_IMAGE)
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): Call<ResponseMessage>
}
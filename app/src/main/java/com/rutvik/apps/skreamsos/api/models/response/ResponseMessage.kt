package com.rutvik.apps.skreamsos.api.models.response

import com.google.gson.annotations.SerializedName

data class ResponseMessage(

    @SerializedName("message")
    val message: String
)
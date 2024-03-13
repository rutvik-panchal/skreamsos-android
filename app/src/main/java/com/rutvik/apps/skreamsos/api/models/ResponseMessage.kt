package com.rutvik.apps.skreamsos.api.models

import com.google.gson.annotations.SerializedName

data class ResponseMessage(

    @SerializedName("message")
    val message: String
)
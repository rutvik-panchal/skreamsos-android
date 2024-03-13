package com.rutvik.apps.skreamsos.api.models

import com.google.gson.annotations.SerializedName

data class SOSResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("doc")
    val doc: Doc
)
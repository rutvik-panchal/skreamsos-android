package com.rutvik.apps.skreamsos.api.models.response

import com.google.gson.annotations.SerializedName
import com.rutvik.apps.skreamsos.api.models.Doc

data class SOSResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("doc")
    val doc: Doc
)
package com.rutvik.apps.skreamsos.api.models

import com.google.gson.annotations.SerializedName

data class SOSAlert (

    @SerializedName("type")
    val type: String = "Point",

    @SerializedName("coordinates")
    val coordinates: ArrayList<Float>
)
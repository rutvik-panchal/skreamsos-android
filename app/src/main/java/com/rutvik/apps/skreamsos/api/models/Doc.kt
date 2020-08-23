package com.rutvik.apps.skreamsos.api.models

import com.google.gson.annotations.SerializedName

data class Doc(

    @SerializedName("location")
    val location: SOSAlert,

    @SerializedName("_id")
    val _id: String,

    @SerializedName("userId")
    val userId: String,

    @SerializedName("__v")
    val __v: String,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("phoneNumber")
    val phoneNumber: String,

    @SerializedName("startTime")
    val startTime: String,

    @SerializedName("updatedAt")
    val updatedAt: String
)
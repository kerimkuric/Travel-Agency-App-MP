package com.example.travelagencyapp.model.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO matching JSONPlaceholder /posts — used as remote "trip packages" for the assignment API.
 */
data class TripRemoteDto(
    val id: Int? = null,
    @SerializedName("userId")
    val destinationIndex: Int,
    val title: String,
    val body: String,
)

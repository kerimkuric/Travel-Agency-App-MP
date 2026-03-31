package com.example.travelagencyapp.model

data class Trip(
    val id: String,
    val title: String,
    val destination: String,
    val days: Int,
    val pricePerPerson: Int,
)


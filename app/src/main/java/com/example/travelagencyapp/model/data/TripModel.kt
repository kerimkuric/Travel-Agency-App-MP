package com.example.travelagencyapp.model.data

data class TripModel(
    val id: String,
    val title: String,
    val destination: String,
    val days: Int,
    val pricePerPerson: Int,
    val category: String,
)


package com.example.travelagencyapp.model.repository

import kotlinx.coroutines.flow.Flow

interface DestinationRepository {
    suspend fun ensureSeeded()
    fun observeDestinationNames(): Flow<List<String>>
}

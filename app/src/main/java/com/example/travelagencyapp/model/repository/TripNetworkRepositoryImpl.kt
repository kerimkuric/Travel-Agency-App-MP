package com.example.travelagencyapp.model.repository

import com.example.travelagencyapp.model.data.TripModel
import com.example.travelagencyapp.model.data.local.entity.TripCategoryCrossRef
import com.example.travelagencyapp.model.data.local.entity.TripEntity
import com.example.travelagencyapp.model.data.remote.api.TravelApiService
import com.example.travelagencyapp.model.data.remote.mappers.toCategoryLink
import com.example.travelagencyapp.model.data.remote.mappers.toRemoteDto
import com.example.travelagencyapp.model.data.remote.mappers.toTripEntity
import com.example.travelagencyapp.model.data.remote.mappers.toTripModel
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.HttpException
import java.io.IOException

@Singleton
class TripNetworkRepositoryImpl @Inject constructor(
    private val api: TravelApiService,
) : TripNetworkRepository {

    override suspend fun fetchTripBatch(): RemoteTripBatch {
        return try {
            val remote = api.getTrips().take(MAX_REMOTE_TRIPS)
            RemoteTripBatch(
                trips = remote.map { it.toTripEntity() },
                categoryLinks = remote.map { it.toCategoryLink() },
            )
        } catch (e: IOException) {
            throw NetworkException("No internet connection. Check your network and try again.", e)
        } catch (e: HttpException) {
            throw NetworkException("Server error (${e.code()}). Please try again later.", e)
        }
    }

    override suspend fun fetchTrip(tripId: Long): TripModel {
        return try {
            api.getTrip(tripId.toInt()).toTripModel()
        } catch (e: IOException) {
            throw NetworkException("No internet connection. Check your network and try again.", e)
        } catch (e: HttpException) {
            throw NetworkException("Could not load trip (${e.code()}).", e)
        }
    }

    override suspend fun createTrip(trip: TripModel): TripModel {
        return try {
            api.createTrip(trip.toRemoteDto()).toTripModel()
        } catch (e: IOException) {
            throw NetworkException("No internet connection. Could not create trip.", e)
        } catch (e: HttpException) {
            throw NetworkException("Server rejected create request (${e.code()}).", e)
        }
    }

    override suspend fun updateTrip(trip: TripModel): TripModel {
        val id = trip.id.toIntOrNull()
            ?: throw NetworkException("Invalid trip id for update.")
        return try {
            api.updateTrip(id, trip.toRemoteDto()).toTripModel()
        } catch (e: IOException) {
            throw NetworkException("No internet connection. Could not update trip.", e)
        } catch (e: HttpException) {
            throw NetworkException("Server rejected update (${e.code()}).", e)
        }
    }

    override suspend fun deleteTrip(tripId: Long) {
        try {
            api.deleteTrip(tripId.toInt())
        } catch (e: IOException) {
            throw NetworkException("No internet connection. Could not delete trip.", e)
        } catch (e: HttpException) {
            throw NetworkException("Server rejected delete (${e.code()}).", e)
        }
    }

    companion object {
        private const val MAX_REMOTE_TRIPS = 15
    }
}

class NetworkException(
    override val message: String,
    cause: Throwable? = null,
) : Exception(message, cause)

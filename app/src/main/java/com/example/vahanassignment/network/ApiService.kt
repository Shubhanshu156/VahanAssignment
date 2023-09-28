package com.example.vahanassignment.network

import com.example.vahanassignment.network.models.UniversityResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/search")
    suspend fun getUniversityList(): Response<UniversityResponse>
}